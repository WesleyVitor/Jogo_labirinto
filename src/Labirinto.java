public class Labirinto {

    private static final char PAREDE_VERTICAL = '|';
    private static final char PAREDE_HORIZONTAL = '-';
    private static final char VAZIO = ' ';
    private static final char INICIO = 'I';
    private static final char DESTINO = 'D';
    private static final int TAMANHO = 10;
    private static final char CAMINHO = '.';
    private static final char SEM_SAIDA = 'x';

    private static int linhaInicio;
    private static int colunaInicio;
    private static int linhaDestino;
    private static int ColunaDestino;

    private static char[][] tabuleiro;
    private static char PAREDE_INTERNA = '@';
    private static double PROBABILIDADE = 0.7;

    public static boolean procurarCaminho(int linhaAtual, int colunaAtual){
        int proxLinha;
        int proxColuna;
        boolean achou = false;
        //tentar subir
        proxLinha = linhaAtual-1;
        proxColuna = colunaAtual;
        achou = tentaCaminho(proxLinha, proxColuna);
        //tentar descer
        if(!achou){
            proxLinha = linhaAtual+1;
            proxColuna = colunaAtual;
            achou = tentaCaminho(proxLinha, proxColuna);
        }
        //tentar ir para esquerda
        if(!achou){
            proxLinha = linhaAtual;
            proxColuna= colunaAtual-1;
            achou = tentaCaminho(proxLinha, proxColuna);
        }
        //tentar ir para direita
        if(!achou){
            proxLinha = linhaAtual;
            proxColuna= colunaAtual+1;
            achou = tentaCaminho(proxLinha, proxColuna);
        }
        return achou;
    }
    public static boolean tentaCaminho(int proxLinha, int proxColuna){
        boolean achou=false;
        if(tabuleiro[proxLinha][proxColuna]==DESTINO){
            achou=true;
        }else if(posicaoVazia(proxLinha, proxColuna)){
            //marcação
            tabuleiro[proxLinha][proxColuna] = CAMINHO;
            imprimir();
            achou = procurarCaminho(proxLinha, proxColuna);
            if(!achou){
                tabuleiro[proxLinha][proxColuna]=SEM_SAIDA;
                imprimir();
            }
        }
        return achou;
    }

    public static boolean posicaoVazia(int linha, int coluna){
        boolean vazio = false;
        if(linha>=0 && coluna >=0 && linha<TAMANHO && coluna<TAMANHO){
            if(tabuleiro[linha][coluna]==VAZIO){
                vazio = true;
            }
        }
        return vazio;
    }

    public static int gerar_numero(int minimo, int maximo){
        int value = (int)Math.round(Math.random()*(maximo-minimo));
        return minimo+value;
    }

    public static void inicializar_Matriz(){
        for(int i=0; i< TAMANHO;i++){
            tabuleiro[i][0] = PAREDE_VERTICAL;
            tabuleiro[i][TAMANHO - 1] = PAREDE_VERTICAL;
            tabuleiro[0][i] = PAREDE_HORIZONTAL;
            tabuleiro[TAMANHO - 1][i] = PAREDE_HORIZONTAL;
        }

        for(int i=1;i<TAMANHO-1;i++){
            for(int j=1;j<TAMANHO-1;j++){
                if(Math.random() > PROBABILIDADE){
                    tabuleiro[i][j]=PAREDE_INTERNA;
                }else{
                    tabuleiro[i][j]=VAZIO;
                }

            }
        }
        linhaInicio = gerar_numero(1, TAMANHO/2-1);
        colunaInicio = gerar_numero(1, TAMANHO/2-1);
        tabuleiro[linhaInicio][colunaInicio]=INICIO;

        linhaDestino = gerar_numero(TAMANHO/2, TAMANHO-2);
        colunaInicio = gerar_numero(TAMANHO/2, TAMANHO-2);
        tabuleiro[linhaDestino][colunaInicio]=DESTINO;
    }
    public static void imprimir() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(tabuleiro[i][j]);
            }
            System.out.println();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        tabuleiro = new char[TAMANHO][TAMANHO];
        inicializar_Matriz();
        imprimir();
        System.out.println("\n- Procurando solução -\n");
        boolean achou = tentaCaminho(linhaInicio, colunaInicio);
        if(achou){
            System.out.println("ACHOU O CAMINHO");
            imprimir();
        }else{
            System.out.println("NÃO TEM CAMINHO");
        }
    }

}
