
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Renato Melo - 16/Jul/25 (Alteração)
 */
public class conectaDAO {
    
    
	

    public Connection connectDB(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc-11?useSSL=false", "adm", "Password2025@");
            // Note a adição de "?useSSL=false" na URL
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!"); // Para depuração
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
            // Você pode querer logar o erro no console também para depuração
            System.err.println("Detalhes do erro de conexão: " + erro.getMessage());
            erro.printStackTrace(); // Imprime o stack trace para mais detalhes
        }
        return conn;
    }

}	