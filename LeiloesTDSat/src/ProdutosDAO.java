import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    private Connection conn;

    public ProdutosDAO() {
        // Tenta estabelecer a conexão ao criar uma instância de ProdutosDAO
        conectaDAO objConectaDAO = new conectaDAO();
        this.conn = objConectaDAO.connectDB();

        // **ADICIONE ESTA VERIFICAÇÃO INICIAL**
        if (this.conn == null) {
            System.err.println("Falha ao inicializar ProdutosDAO: Conexão com o banco de dados é nula.");
            // Opcional: Lançar uma RuntimeException se a conexão for crítica para a operação
            // throw new RuntimeException("Não foi possível conectar ao banco de dados.");
        }
    }

    public void cadastrarProduto(ProdutosDTO produto) {
        // Verifique a conexão antes de qualquer operação de banco de dados
        if (this.conn == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco de dados para cadastrar o produto.");
            System.err.println("Tentativa de cadastrar produto com conexão nula.");
            return; // Sai do método se a conexão for nula
        }

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = this.conn.prepareStatement(sql)) {
            pstm.setString(1, produto.getNome());
            pstm.setDouble(2, produto.getValor()); // Altere para setDouble se for double/float
            pstm.setString(3, produto.getStatus());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso! ✅");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar o produto. Nenhuma linha afetada.");
            }
	    
	    
	    

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o Produto: " + erro.getMessage());
            System.err.println("Erro SQL ao cadastrar produto: " + erro.getMessage());
            erro.printStackTrace(); // Para ver o stack trace completo no console
        } finally {
            // Idealmente, a conexão deveria ser fechada em um local apropriado,
            // ou gerenciada por um pool de conexões em aplicações maiores.
            // Para um app simples, pode fechar aqui se a conexão for única para cada operação.
            // No entanto, se 'conn' for uma variável de instância, geralmente é melhor
            // fechar a conexão quando a aplicação estiver sendo encerrada.
            // Cuidado ao fechar a conexão aqui se ela for reutilizada por outros métodos DAO!
        }
    }

    // Método para fechar a conexão quando o DAO não for mais necessário
    public void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
                System.out.println("Conexão com o banco de dados fechada. 🔒");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}