import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static Object[][] produtos = new Object[10][10];
    private static int contador = 0;


    public static void main(String[] args) {
        do {
            menu();
        } while (true);
    }


    public static void menu() {

        Scanner sc = new Scanner(System.in);
        System.out.printf("Menu\n" +
                "\t 1 - Cadastrar/Comprar produtos\n" +
                "\t 2 - Imprimir estoque\n" +
                "\t 3 - Listar os produto pelo Tipo\n" +
                "\t 4 - Sair\n");

        switch (sc.next()) {
            case "1":
                cadastrar();
                break;
            case "2":
                imprimir();
                break;
            case "3":
                listarPorTipos(2,3);
                break;
            case "4":
                return;
            default:
                System.out.println("Opção inválida, informe novamente.");
        }
    }

    public static void cadastrar() {
//        Variaveis
        Scanner sc = new Scanner(System.in);
        int posicaoItens;
        String identificador;
        String marca;
        final int IDENTIFICADOR = 0;
        final int MARCA= 1;
        final int TIPO = 2;
        final int NOME = 3;
        final int PRECO_CUSTO = 4;
        final int QUANTIDADE = 5;
        final int DATA_COMPRA = 6;
        final int PRECO = 7;
        final int ESTOQUE= 8;
        final int FLUXO_DE_CAIXA = 9;

        System.out.println("Bem vindo ao Cadastro!\n");

        System.out.println("Digite o Identificador do Produto");
        identificador = sc.nextLine();

        System.out.println("Digite a Marca do Produto");
        marca = sc.nextLine();

//        validação de cadastro ou compra
        posicaoItens = buscaPosicao(identificador);
        if(posicaoItens <0){
            posicaoItens = contador;
            produtos[posicaoItens][ESTOQUE] = 0;
        }
        produtos[posicaoItens][IDENTIFICADOR] = identificador;
        produtos[posicaoItens][MARCA] = marca;

        System.out.println("Digite o Tipo do Produto(ALIMENTO, BEBIDA OU HIGIENE)");
        ProductType produto = null;
        do {
            try {
                String tipo = sc.nextLine();
                produto = ProductType.valueOf(tipo);
                produtos[posicaoItens][TIPO] = produto;
            }catch(Exception exception){

            }
        }while(produto == null);

        System.out.println("Digite o Nome do Produto (ex.: Coca Cola)");
        produtos[posicaoItens][NOME] = sc.nextLine();


        System.out.println("Digite o Preço Custo do Produto (ex.: 2,6)");
        do{
            produtos[posicaoItens][PRECO_CUSTO] = sc.nextDouble();
            if ((double) produtos[posicaoItens][PRECO_CUSTO] < 0.0){
                System.out.println("Preço inválido, entre novamente.");
            }
        }while((double)produtos[posicaoItens][PRECO_CUSTO]<=0.0);

        System.out.println("Digite a Quantidade do Produto (ex.: 2)");
        do {
            produtos[posicaoItens][QUANTIDADE] = sc.nextInt();
            if ((int)produtos[posicaoItens][QUANTIDADE]<0){
                System.out.println("Quantidade negativa, informe novamente");
            }
        }while ((int)produtos[posicaoItens][QUANTIDADE]<0);

        produtos[posicaoItens][DATA_COMPRA] = LocalDateTime.now();

        produtos[posicaoItens][PRECO] = (Double) produtos[posicaoItens][PRECO_CUSTO] * ProductType.valueOf((String) produtos[posicaoItens][TIPO]).getMarkup();

        atualizarEstoque(posicaoItens, QUANTIDADE, ESTOQUE);
        fluxoDeCaixa(posicaoItens, PRECO_CUSTO, QUANTIDADE, FLUXO_DE_CAIXA);

        contador++;

        if (contador == produtos.length) {
            produtos = (Object[][]) aumentarMatriz(produtos);
        }

    }

    private static void fluxoDeCaixa(int posicaoItens, int PRECO_CUSTO, int QUANTIDADE, int FLUXO_DE_CAIXA) {
        produtos[posicaoItens][FLUXO_DE_CAIXA] = (int)produtos[posicaoItens][QUANTIDADE] * (double)produtos[posicaoItens][PRECO_CUSTO]*-1.0;
    }

    private static void atualizarEstoque(int posicaoItens, int QUANTIDADE, int ESTOQUE) {
        if (produtos[posicaoItens][ESTOQUE] == null) {
            produtos[posicaoItens][ESTOQUE] = produtos[posicaoItens][QUANTIDADE];
        } else {
            produtos[posicaoItens][ESTOQUE] = (int) produtos[posicaoItens][ESTOQUE] + (int) produtos[posicaoItens][QUANTIDADE];
        }
    }


    public static void imprimir() {
        System.out.println("Bem vindo ao Relatorio!");
        System.out.println(contador);

        for (int i = 0; i < contador; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(produtos[i][j] + "\t ");
            }
            System.out.println();
        }
    }

    private static int buscaPosicao(String identificador) {
        for (int i = 0; i < contador; i++) {
            if (produtos[i][0].equals(identificador)){
                contador--;
                return i;
            }
        }
        return -1;
    }

    public static void listarPorTipos(int TIPO, int MARCA) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem vindo a Listagem!");
        System.out.print("Escolhe o tipo de produto a ser filtrado - Opções: ");

        for (ProductType itens : ProductType.values()) {
            System.out.print(itens + " ");
        }

        String tipo = sc.nextLine().toUpperCase();
        System.out.printf("Listando produtos do tipo %s%n", tipo );
        for (int i = 0; i < contador; i++) {
            if (produtos[i][TIPO].equals(tipo)) {
                System.out.print(produtos[i][MARCA] + " ");
            }
        }
        System.out.println();
    }

    private static Object aumentarMatriz(Object[][] produtos) {
        Object[][] novoArray = new Object[produtos.length + 10][10];
        for (int i = 0; i < produtos.length; i++) {
            for (int j = 0; j < produtos[i].length; j++) {
                novoArray[i][j] = produtos[i][j];
            }
        }
        return novoArray;
    }
}


//        Supermercado
//
//FASE 1
//        ________________________________________________________________________________________________________________________
//        PRODUTOS
//        - O sistema deve permitir a compra/cadastro de produtos
//        - Deve solicitar os seguintes dados
//        Tipo
//        Marca
//        Identificador
//        Nome
//        Preco Custo
//        Quantidade
//
//        `- O sistema deve verificar se o produto ja existe, se sim atualizar os dados, se não armazenar um novo produto
//
//        - O cadastro de produto devera armazenar também os seguintes dados
//        Data Compra
//        Preco de venda
//        Estoque
//
//        - O preco de venda deve ser calculado a partir do preco de custo, usando o markup de cada tipo de produto
//
//        - O estoque deve ser atualizado a cada compra.
//
//        - Armazenar os produtos em uma matriz
//
//        - Caractetriscas do produto:
//
//        Tipo: (Enum) ALIMENTOS - markup 1.2 , BEBIDA - markup 2.3, HIGIENTE - markup 1.5
//        Marca: (String)
//        Identificador: (String)
//        Nome: (String)
//        Preco Custo: (Double)
//        Quantidade: (int)
//        Data Compra: (LocalDatetime)
//        Preco: (Double) deve ser calculado a patir do preco de custo, markup cada tipo de produto tem o seu markup
//        Estoque: (int)
//
//        Criar um menu de acesso ao sistema
//
//        1 - Cadastrar/Comprar produtos
//        2 - Imprimir estoque
//        3 - Listar os produto pelo Tipo
//
//        OBS:
//        - Quatidade de protutos nao pode ser negativa, e os precos de custo também
//        - Usar o redimensionamento...
//
//        +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
