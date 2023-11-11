package pedidopronto.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pedidopronto.model.Produto;

public class ProdutoRepository {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/SQLdb";
        String user = "root";
        String password = "nova_senha";
        return DriverManager.getConnection(url, user, password);
    }

    public void create(Produto produto) {
        String sql = "INSERT INTO cad_produto (id, nome, descricao, preco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, produto.getId());
            statement.setString(2, produto.getNome());
            statement.setString(3, produto.getDescricao());
            statement.setDouble(4, produto.getPreco());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> read() {
        String sql = "SELECT * FROM cad_produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                double preco = resultSet.getDouble("preco");
                produtos.add(new Produto(id, nome, descricao, preco));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void update(Produto produto) {
        String sql = "UPDATE cad_produto SET nome = ?, descricao = ?, preco = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getPreco());
            statement.setInt(4, produto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM cad_produto WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
