package livraria.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import livraria.model.Empre_livro;
import livraria.model.Emprestimo;

public class DaoEmpre_livro {
    public boolean inserir(Empre_livro empre_livro){
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            String insert = "INSERT into empre_livro (id, emprestimo, livro) values (?, ?, ?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(insert);
            preparedStatement1.setInt(1, empre_livro.getId());
            preparedStatement1.setInt(2, empre_livro.getEmprestimo().getId());
            preparedStatement1.setInt(3, empre_livro.getLivro().getId());
            int resultado = preparedStatement1.executeUpdate();
            if(resultado > 0){
                return true;
            }else{
                return false;
            }
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
        
    }
    public boolean alterar(Empre_livro empre_livro){
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            String update = "UPDATE empre_livro set emprestimo = ?, livro = ? where id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(update);
            preparedStatement1.setInt(3, empre_livro.getId());
            preparedStatement1.setInt(1, empre_livro.getEmprestimo().getId());
            preparedStatement1.setInt(2, empre_livro.getLivro().getId());
            int resultado = preparedStatement1.executeUpdate();
            if(resultado > 0){
                return true;
            }else{
                return false;
            }
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
    }
    public boolean excluir(Empre_livro empre_livro){
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            String delete = "Delete from empre_livro where id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(delete);
            preparedStatement1.setInt(1, empre_livro.getId());
            int resultado = preparedStatement1.executeUpdate();
            if(resultado > 0){
                return true;
            }else{
                return false;
            }
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
    }

    public List<Empre_livro> pesquisar(int id){
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            String consulta = "SELECT * from empre_livro where id = ?";
            Empre_livro empre_livro = new Empre_livro();
            List<Empre_livro> lista = new ArrayList<Empre_livro>();
            PreparedStatement preparedStatement1 = connection.prepareStatement(consulta);
            preparedStatement1.setInt(1, empre_livro.getId());
        }
    }
}

