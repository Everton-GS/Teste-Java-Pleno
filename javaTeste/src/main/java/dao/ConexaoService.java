package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Usuario;
public class ConexaoService {
	
	
	String url="jdbc:postgresql://localhost/db_user";
	
	String login="postgres";
	
	String senha="java";

	String insert="Insert into usuario (login,senha) values (?,?)";
	
	String consultarUsuario="Select * from usuario where login=?";
	
	String consultarUsuarioId="Select * from usuario where id=?";
	
	String deletarUsuario="Delete from usuario where id=?";
	
	String atualizarDado="Update usuario set login=?,senha=? where id=?";
	
	String listarUsuario="Select * from usuario";
	
	
	public void registrarUsuario(String login,String senha) {
		try {
			Connection conexao = DriverManager.getConnection(url,this.login,this.senha);
			conexao.setAutoCommit(false);
			PreparedStatement consultarUsuarioBd=conexao.prepareStatement(consultarUsuario);
			consultarUsuarioBd.setString(1, login);
			ResultSet resultadoConsulta=consultarUsuarioBd.executeQuery();
			
			if(resultadoConsulta.next()) {
			  System.out.println("Escolha outro login, pois o mesmo esta em utilização por outro usuário");
			  consultarUsuarioBd.close();
			  conexao.close();
			}else {
				PreparedStatement insertUserPreparedStatement = conexao.prepareStatement(insert);
				insertUserPreparedStatement.setString(1, login);
				insertUserPreparedStatement.setString(2, senha);
				conexao.commit();
				insertUserPreparedStatement.execute();
				insertUserPreparedStatement.close();
				conexao.close();
				System.out.println("Usuário registrado no sistema.");
			}
		}catch(SQLException e) {
			System.out.println("Falha na criação do usuário");
		}
	}
	
	public ArrayList<Usuario> listarUsuario() {
		try {
			Connection conexao = DriverManager.getConnection(url,this.login,this.senha);
			PreparedStatement listar = conexao.prepareStatement(listarUsuario);
			
			ResultSet resultado=listar.executeQuery();
			ArrayList<Usuario> usuario= new ArrayList<>();
			
			while(resultado.next()) {
				Usuario listaU = new Usuario();
				listaU.setId(resultado.getInt("id"));
				listaU.setLogin(resultado.getString("login"));
				listaU.setSenha(resultado.getString("senha"));
				usuario.add(listaU);
			}

			return usuario;
		}catch(SQLException e) {
			System.out.println("Falha ao realizar solicitação da listar usuário");
		}
		return null;
	}
	
	public void deletarUsuario(int id) {
		try {
			Connection conexao = DriverManager.getConnection(url,this.login,this.senha);
			conexao.setAutoCommit(false);
		    PreparedStatement consultarID = conexao.prepareStatement(consultarUsuarioId);
			consultarID.setInt(1, id);
			
			ResultSet resultadoConsulta = consultarID.executeQuery();
			
			if(!resultadoConsulta.next()) {
				System.out.println("O usuário não foi encontrado no banco de dados.");
				consultarID.close();
				conexao.close();
			}else {
				PreparedStatement deletarUser = conexao.prepareStatement(deletarUsuario);
				deletarUser.setInt(1, id);
				deletarUser.execute();
				deletarUser.close();
				consultarID.close();
				conexao.commit();
				conexao.close();
				System.out.println("Usuário deletado com sucesso");	
			}
		}catch(SQLException e) {
			System.out.println("Falha na deletação do usuário");
	   }
		
	}
	
	public void atualizarDadosUsuario(int id,String login,String senha) {
		try {
			Connection conexao = DriverManager.getConnection(url,this.login,this.senha);
			conexao.setAutoCommit(false);
			PreparedStatement consultarUsuarioBd=conexao.prepareStatement(consultarUsuario);
			consultarUsuarioBd.setString(1, login);
			ResultSet resultadoConsulta=consultarUsuarioBd.executeQuery();
			if(resultadoConsulta.next()) {
				  System.out.println("Escolha outro login, pois o mesmo esta em utilização por outro usuário");
				  consultarUsuarioBd.close();
				  conexao.close();
				}else {
					PreparedStatement consultarUsuario =conexao.prepareStatement(consultarUsuarioId);
					consultarUsuario.setInt(1, id);
					ResultSet resultadoConsultaID = consultarUsuario.executeQuery();
			
					if(resultadoConsultaID.next()) {
					 	PreparedStatement atualizarDados =conexao.prepareStatement(atualizarDado);
					 	atualizarDados.setString(1, login);
					 	atualizarDados.setString(2, senha);
					 	atualizarDados.setInt(3, id);
					 	atualizarDados.execute();
					 	consultarUsuario.close();
					 	atualizarDados.close();
						conexao.commit();
						conexao.close();
					 	System.out.println("Dados atualizado");
		         }else {
		                System.out.println("Usuário não encontrado pelo id");	
		            	}		        
			}
		}catch(SQLException e) {
			System.out.println("Atualização de dados falhou");
		}
		
	}
}
