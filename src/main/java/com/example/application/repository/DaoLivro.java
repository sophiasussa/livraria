package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.model.Livro;
import com.example.application.model.Autor;
import com.example.application.model.Editora;

public class DaoLivro {
	public boolean inserir(Livro livro) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			String insert = "INSERT INTO livro (id, nome_livro, descricao, ano_publicacao, id_autor, id_editora) values"
					+ "(?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement1 = connection.prepareStatement(insert);
			preparedStatement1.setInt(1, livro.getId());
			preparedStatement1.setString(2, livro.getNome_livro());
			preparedStatement1.setString(3, livro.getDescricao());
			preparedStatement1.setInt(4, livro.getAno_publicacao());
			preparedStatement1.setInt(5, livro.getAutor().getId());
			preparedStatement1.setInt(6, livro.getEditora().getId());
			int resultado = preparedStatement1.executeUpdate();
			if (resultado > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean alterar(Livro livro) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			String update = "UPDATE livro set nome_livro = ?, descricao = ?, ano_publicacao  = ?, id_autor = ?, id_editora = ? where id = ?";
			PreparedStatement preparedStatement1 = connection.prepareStatement(update);
			preparedStatement1.setInt(6, livro.getId());
			preparedStatement1.setString(1, livro.getNome_livro());
			preparedStatement1.setString(2, livro.getDescricao());
			preparedStatement1.setInt(3, livro.getAno_publicacao());
			preparedStatement1.setInt(4, livro.getAutor().getId());
			preparedStatement1.setInt(5, livro.getEditora().getId());
			int resultado = preparedStatement1.executeUpdate();
			if (resultado > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(Livro livro) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			String delete = "DELETE from livro where id = ?";
			PreparedStatement preparedStatement1 = connection.prepareStatement(delete);
			preparedStatement1.setInt(1, livro.getId());
			int resultado = preparedStatement1.executeUpdate();
			if (resultado > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public Livro pesquisar(int id) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			String consulta = "SELECT * from livro where id = ?";
			Livro livro = new Livro();
			PreparedStatement preparedStatement = connection.prepareStatement(consulta);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				livro.setId(resultSet.getInt("id"));
				livro.setNome_livro(resultSet.getString("nome_livro"));
				livro.setDescricao(resultSet.getString("descricao"));
				livro.setAno_publicacao(resultSet.getInt("ano_publicacao"));
				Autor autor = new DaoAutor().pesquisar(resultSet.getInt("id_autor"));
				Editora editora = new DaoEditora().pesquisar(resultSet.getInt("id_editora"));
				livro.setAutor(autor);
				livro.setEditora(editora);
			}
			return livro;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Livro> pesquisarTodos() {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			String consulta = "SELECT * from livro";
			List<Livro> lista = new ArrayList<Livro>();
			Livro livro;
			PreparedStatement preparedStatement = connection.prepareStatement(consulta);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				livro = new Livro();
				livro.setId(resultSet.getInt("id"));
				livro.setNome_livro(resultSet.getString("nome_livro"));
				livro.setDescricao(resultSet.getString("descricao"));
				livro.setAno_publicacao(resultSet.getInt("ano_publicacao"));
				Autor autor = new DaoAutor().pesquisar(resultSet.getInt("id_autor"));
				Editora editora = new DaoEditora().pesquisar(resultSet.getInt("id_editora"));
				livro.setAutor(autor);
				livro.setEditora(editora);
				lista.add(livro);
			}
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

}
