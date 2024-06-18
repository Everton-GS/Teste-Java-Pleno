package br.com.comunidadeXteste.maven;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ConexaoService;
import entity.Usuario;

public class Application {

    public static void main(String[] args) {
      
      Scanner keyboard = new Scanner(System.in);
		
	  ConexaoService conexaoService= new ConexaoService();
	  int numero;
	     do{
		    System.out.println("******************************** Menu ********************************");
            System.out.println("* Digite 1 para registrar um usuário.                                *");
            System.out.println("* Digite 2 para excluir um usuário pelo id.                          *");
            System.out.println("* Digite 3 para atualizar os dados de um usuário pelo id.            *");
            System.out.println("* Digite 4 para listar usuários.                                     *");
            System.out.println("* Digite 5 para sair.                                                *");
            System.out.println("**********************************************************************");
			System.out.print("Digite a opção:");
			numero=keyboard.nextInt();
			keyboard.nextLine();

			switch(numero){
				case 1:
				System.out.print("Digite seu nome:");
				String nome=keyboard.nextLine();

				System.out.print("Digite sua senha:");
				String senha=keyboard.nextLine();

				conexaoService.registrarUsuario(nome, senha);
				break;

				case 2:
				System.out.print("Digite o id do usuário:");
				int id=keyboard.nextInt();
				conexaoService.deletarUsuario(id);
				break;

				case 3:
				System.out.print("Digite o id do usuário:");
				int idA=keyboard.nextInt();
				keyboard.nextLine();

				System.out.print("Digite o novo login do usuário:");
				String login=keyboard.nextLine();

				System.out.print("Digite a nova senha:");
				String senhaA=keyboard.nextLine();

				conexaoService.atualizarDadosUsuario(idA, login, senhaA);
				break;

				case 4:
				System.out.println("Lista de usuários");
				ArrayList<Usuario> usuario =conexaoService.listarUsuario();
				System.out.println(usuario);
				break;

				case 5:
				System.out.println("Sistema delogando.........");
				break;

				default:
				System.out.println("Opção inválida.");
				break;
			}
	  }while(numero!=5);
	  keyboard.close();
 }
}
  