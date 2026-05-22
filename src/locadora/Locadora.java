
package locadora;

import DAO.filmeDAO;
import DAO.relatorioDAO;
import DAO.usuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.filme;
import model.usuario;
import util.exportador;
import util.importador;

public class Locadora {


    public static void main(String[] args) throws SQLException, IOException {
        filmeDAO dao = new filmeDAO();
        Scanner teclado = new Scanner(System.in);
        
        int opcao = 0;
        
        System.out.println(" --------------------------------  ");
        System.out.println(" | ACESSO LIMITADO -> SENACFLIX |  ");
        System.out.println(" --------------------------------  ");
        
        usuarioDAO userDao = new usuarioDAO();
        
//usuario admin = new usuario();
//admin.setLogin("admin");
//userDao.cadastrouser(admin, "admin");
        
        boolean logado = false;
        int tentativas = 0;
        
        while(!logado && tentativas < 3) {
            
            System.out.println("Login: ");
            String login = teclado.nextLine();
            
            System.out.println("Senha: ");
            String senha = teclado.nextLine();
            
            if(userDao.autenticacao(login, senha)){
                
                System.out.println("Acesso permitido! Bem-vindo, "+login+".");
                logado = true;
            }else{
                tentativas++;
                System.out.println("Login ou senha incorretos. Tentativa "+tentativas + " de 3");
            }
            
        }
        if(!logado){
            
            System.out.println("Você excedeu o número máximo de tentativas! Tente novamente mais tarde.");
            System.out.println("-- SISTEMA ENCERRADO --");
            System.exit(0);
        }
        
        do{
            
            
            System.out.println("=== Bem-vindo a locadora senacflix ===");
            System.out.println("1 - Cadastrar filmes (alocar)");
            System.out.println("2 - Cadastrar gênero");
            System.out.println("3 - Abrir lista de filmes");
            System.out.println("4 - Alterar o status do filme (alugado/disponivel)");
            System.out.println("5 - Deletar filme");
            System.out.println("6 - Alocação de filmes");
            System.out.println("7 - Consultar estatisticas gerais");
            System.out.println("8 - Exportação ");
            System.out.println("9 - Importação ");
            System.out.println("0 - Sair da locadora");
            System.out.println("======================================");
            
            
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                
               case 1: // FUNÇÃO P CADASTRAR FILMES
                   
    System.out.println("-- CADASTRAR NOVO FILME --");
    System.out.println("Nome do filme: ");
    String nome_filme = teclado.nextLine();
    
    System.out.println("Faixa etária: ");
    String faixa_etaria = teclado.nextLine();
    
    System.out.println("Ano: ");
    int ano_filme = teclado.nextInt();
    teclado.nextLine(); 
    
    System.out.println("Status (disponivel/locado): ");
    String status_filme = teclado.nextLine();
    
    System.out.println("Digite o NOME do gênero (ex: Romance, Ação): ");
    String nomeGen = teclado.nextLine();
    
    int idEncontrado = dao.consultarIdGenero(nomeGen);
    
    if (idEncontrado != -1) {
        filme filmeNovo = new filme(nome_filme, faixa_etaria, ano_filme, status_filme);
        model.genero gen = new model.genero();
        gen.setId(idEncontrado); 
        filmeNovo.setCategoria(gen);
        
        dao.cadastrar(filmeNovo);
        System.out.println(" Filme '" + nome_filme + "' cadastrado com sucesso!");
    } else {
        System.out.println(" Erro: O gênero '" + nomeGen + "' não existe no banco de dados!");
    }
    break;
                    
               case 2: // FUNÇÃO DE CADASTRO DE GÊNERO
                   
                   System.out.println("-- CADASTRAR NOVO GÊNERO --");
                   System.out.println("Nome do gênero: ");
                   String nome_genero = teclado.nextLine();
                   
                   model.genero novoGen = new model.genero();
                   novoGen.setNome(nome_genero);
                   dao.cadastrargen(novoGen);
                   
                   break;
    
        case 3: // FUNÇÃO DE LISTAR OS FILMES
    System.out.println("=== COMO DESEJA LISTAR OS FILMES? ===");
    System.out.println("1 - Ver todos");
    System.out.println("2 - Filtrar por Gênero");
    System.out.println("3 - Filtrar por Status (locado/disponivel)");
    System.out.println("0 - Voltar");
    
    int escolhaLista = averiguacao(teclado);
    teclado.nextLine(); 
    List<filme> listaFilmes = null; 

    switch (escolhaLista) {
        case 1:
            listaFilmes = dao.listar();
            break;
            
        case 2:
            System.out.println("Digite o Gênero desejado: ");
            String genBusca = teclado.nextLine();
            listaFilmes = dao.listarPorGenero(genBusca);
            break;
            
        case 3:
            System.out.println("Digite o Status (locado/disponivel): ");
            String statusBusca = teclado.nextLine();
            listaFilmes = dao.listarPorStatus(statusBusca);
            break;
            
        case 0:
            System.out.println("Voltando ao menu principal...");
            break;
            
        default:
            System.out.println("Opção inválida.");
            break;
    }
    if (listaFilmes != null) {
        if (listaFilmes.isEmpty()) {
            System.out.println("Nenhum filme encontrado para essa busca.");
        } else {
            System.out.println("\n--- RESULTADO DA BUSCA ---");
            for (filme f : listaFilmes) {
                System.out.println(f.toString());
            }
            System.out.println("Total: " + listaFilmes.size() + " filmes.\n");
        }
    }
    break;
                    
                
                case 4: // FUNÇÃO DE ALTERAR OS STATUS DOS MEUS FILMES
                    System.out.println(" Digite o ID do filme que você deseja alterar o status: ");
                    int IDFAtualizar = teclado.nextInt();
                    teclado.nextLine();
                    String statusfilmeatt;
                    
                    do{
                        
                        System.out.println(" Digite o status que você deseja setar: (locado/disponivel)");
                        statusfilmeatt = teclado.nextLine();
                        
                        if(!statusfilmeatt.equalsIgnoreCase("disponivel") && !statusfilmeatt.equalsIgnoreCase("locado")){
                            System.out.println("Erro: Status inválido! Por favor, digite exatamente como solicitado.");
                        }
                        
                        
                    } while (!statusfilmeatt.equalsIgnoreCase("disponivel") && !statusfilmeatt.equalsIgnoreCase("locado"));
                    
                    filme filmeAtualizado = new filme();
                    filmeAtualizado.setId_filme(IDFAtualizar);
                    filmeAtualizado.setStatus(statusfilmeatt);
                    dao.atualizar(filmeAtualizado);
                    
                    System.out.println(" O status foi atualizado com sucesso. ");
                    
                break;
                
                case 5: // FUNÇÃO P REMOVER UM FILME DO MENU
                    System.out.println("Digite o ID do filme que você deseja remover da locadora: ");
                    int deletarid = teclado.nextInt();
                    teclado.nextLine();
                    dao.deletar(deletarid);
                    
                break;
                
                case 6: // MÉTODO DE ALUGUEL DE FILMES
                    
                    System.out.println("==== MENU DE ALOCAÇÃO ====");
                    System.out.println("1 - locar um filme (emprestar)");
                    System.out.println("2 - devoulução de um filme (disponibilizar)");
                    System.out.println("0 - retornar ao menu anterior");
                    
                    int subopcao1 = averiguacao(teclado);
                    teclado.nextLine();
                    
                    switch(subopcao1) {
                        
                        case 1: // ALOCAR FILMES

                        System.out.println(" *** ALOCAÇÃO DE FILME ***");
                        System.out.println("ID do filme: ");
                        int idAlocacao = averiguacao(teclado); 
                        teclado.nextLine(); 

    
                        filme filmeAlocacao = dao.buscaID(idAlocacao);

                        if (filmeAlocacao == null) {
                            System.out.println("Filme não encontrado! Tente novamente.");
                        } else if (!filmeAlocacao.getStatus().equalsIgnoreCase("disponivel")) {
       
                            System.out.println("ERRO: O filme '" + filmeAlocacao.getTitulo() + "' já se encontra " + filmeAlocacao.getStatus());
                        } else {
       
                            if (dao.alocacao(idAlocacao, "Locado")) {
                                System.out.println("Sucesso! O filme '" + filmeAlocacao.getTitulo() + "' foi locado com sucesso.");
                            } else {
                                System.out.println("Falha ao alocar.");
                            }
                        }
                        break;

                        case 2: // DEVOLVER O FILME
                            
                            System.out.println(" *** DEVOLUÇÃO FILME ***");
                            System.out.println("ID do filme: ");
                            int idDevolvido = averiguacao(teclado);
                            teclado.nextLine();
                            
                            filme filmedevolucao = dao.buscaID(idDevolvido);
                            
                            if(filmedevolucao == null){
                                System.out.println("Filme não encontrado! Tente novamente.");
                            } else if(filmedevolucao.getStatus().equalsIgnoreCase("disponivel")){
                                System.out.println("ERRO: O filme" + filmedevolucao.getTitulo() + " já se encontra na nossa lista de filmes");
                                
                            } else {
                                if (dao.devolver(idDevolvido)){
                                    System.out.println(" SUCESSO: O filme " + filmedevolucao.getTitulo() + " retornou a nossa lista de filmes com sucesso.");
                                } else{
                                    System.out.println("Falha ao devolver.");
                                }
                                
                            }
                            
                            break;
                            
                        case 0: // VOLTA PRO MENU PRINCIPAL
                             System.out.println("Voltando ao menu principal");
                            break;
                        
                    }
                    
                case 7: // MÉTODO P ABRIR AS ESTATISTICAS GERAIS DO SENACFLIX
    System.out.println("--- PAINEL DE ESTATISTICAS SENACFLIX ---");
    
    try {
        relatorioDAO relatorioDao = new relatorioDAO();
        
        int totalAcervo = relatorioDao.getTotalFilmes();
        
        int totalDisponivel = relatorioDao.getQtdPorStatus("disponivel");
        int totalLocados = relatorioDao.getQtdPorStatus("locado");
        
        System.out.println("Total de filmes: " + totalAcervo);
        System.out.println("Filmes prontos para alugar: " + totalDisponivel);
        System.out.println("Filmes atualmente com clientes: " + totalLocados);
            
        if (totalAcervo > 0) {
            double taxaOcupacao = ((double) totalLocados / totalAcervo) * 100;
            System.out.printf("Taxa de ocupação: %.1f%%\n", taxaOcupacao);
            
            
            if (taxaOcupacao > 70) {
                System.out.println(" AVISO: Alta demanda! Hora de aumentar o catálogo.");
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao carregar estatisticas: " + e.getMessage());
    }
    
    System.out.println("-------------------------------------------");
    break;
                
                case 8: // MÉTODO P FAZER A EXPORTAÇÃO
                    
                    System.out.println(" --- EXPORTADOR ) ---");
                    System.out.println("Digite o nome do ficheiro : ");
                    String nomeArquivo = teclado.nextLine();
                    String exportacao = nomeArquivo + ".csv";
                    
                    exportador exportador = new exportador();
                    exportador.exportarFilmes(exportacao);
                    
                    break;
                    
                    case 9: // MÉTODO P FAZER A IMPORTAÇÃO
                    System.out.println(" --- IMPORTADOR --- ");
                    System.out.println("Digite o nome do ficheiro (NÃO SE ESQUEÇA DE ESCREVER COMO NO EXEMPLO: exemplo.csv): ");
                    String importacao = teclado.nextLine();
                    
                    importador importador = new importador();
                    importador.importarFilmes(importacao);
                    break;  
                
              case 0: //ENCERRA O SISTEMA
                    System.out.println("Fechando senacflix...");
                    break;
                    default:
                    System.out.println("Opção inválida! Escolha outra opção.");
            
            }
            
        } while (opcao != 0);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // método averiguação, posso ignorar ele aqui em baixo (é só pra conseguir usar ele :) )
    
    public static int averiguacao(Scanner teclado) {
        boolean dadosValidos = false;
        int numero = 0;
        while (!dadosValidos) {
            try {
                numero = teclado.nextInt();
                dadosValidos = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO! Digite apenas números.");
                teclado.next();
            }
        }
        return numero;
    }
        
    }
    

