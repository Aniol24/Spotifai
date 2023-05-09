package persistance;

import business.entities.User;
import persistance.exceptions.MaxConnectionsReachedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabaseDAO implements UserDAO {
    private final DDBBAccess ddbbAccess;

    /**
     * Constructor
     * @param ddbbAccess Database access
     */
    public UserDatabaseDAO(DDBBAccess ddbbAccess) {
        this.ddbbAccess = ddbbAccess;
    }

    /**
     * @param user User to add
     * @return true if the user has been added successfully
     */
    @Override
    public boolean addUser(User user) {
        String query = "INSERT INTO usuario(email, nom, password) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = ddbbAccess.getConnection().prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUser());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
        }
        return false;
    }

    public boolean checkUserByName(String nom, String password) {
        String query = "SELECT * FROM usuario WHERE nom = ? AND password = ?";
        try {
            PreparedStatement statement = ddbbAccess.getConnection().prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true; // El usuario existe
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar el usuario: " + e.getMessage());
        }
        return false; // El usuario no existe
    }




    public boolean checkUserByEmail(String email, String password){
        String query = "SELECT * FROM usuario WHERE email = ? AND password = ?";
        try {
            PreparedStatement statement = ddbbAccess.getConnection().prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar el usuario: " + e.getMessage());
        }
        return false;
    }
}
