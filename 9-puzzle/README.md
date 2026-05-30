1. Formulação do Problema

O problema abordado é o clássico Quebra-Cabeça Deslizante (frequentemente chamado de 9-Puzzle), composto por um tabuleiro 3x3 com 8 peças numeradas e um espaço vazio.
Nesta formulação:

    Estado: É representado por uma matriz de inteiros int[][] 3x3, onde o número 0 representa o espaço vazio. A classe State também armazena a posição exata (linha e coluna) do espaço vazio para otimizar a geração de movimentos, além de um ponteiro para o estado "pai" (necessário para reconstruir o caminho da solução).

    Teste de Objetivo: O problema é resolvido quando a matriz atual for perfeitamente igual à matriz objetivo configurada (neste caso, o 0 na primeira posição, seguido dos números de 1 a 8 em ordem).

    Função Sucessora: Consiste em localizar o espaço vazio (0) e gerar novos estados deslizando as peças adjacentes para dentro dele. As ações possíveis são: UP, DOWN, LEFT e RIGHT.

2. Técnica Utilizada

A técnica implementada foi a Busca em Largura (BFS - Breadth-First Search).
Diferente da Busca em Profundidade, a BFS explora a árvore de estados nível por nível. Para isso, o algoritmo utiliza uma Fila (Queue), que obedece à regra FIFO (First In, First Out). Os novos estados gerados (filhos) são sempre adicionados ao final da fila. Isso garante matemática e logicamente que, ao encontrar o estado objetivo, o caminho percorrido será o caminho mais curto possível (ótimo).

Para otimização e prevenção de loops infinitos, foi implementada uma estrutura de HashSet que armazena os estados já visitados. A linguagem Java exige que métodos equals e hashCode sejam sobrescritos na classe State utilizando Arrays.deepEquals, garantindo que matrizes com a mesma configuração de peças sejam reconhecidas como o mesmo estado na memória.
3. Principais Trechos do Código

A arquitetura orientada a objetos dividiu o problema em duas áreas principais: o gerenciamento do estado individual e a execução do algoritmo de busca.

O rastreio do caminho (O Ponteiro "Pai"):
Dentro da classe State, ao gerar um estado sucessor, a matriz atual é clonada e a referência do estado originador é passada adiante. Isso cria uma lista encadeada de trás para frente.
Java

// Exemplo da geração de um filho movendo a peça de cima (UP)
State newState = new State(newBoard, this.row - 1, this.col, this, Action.UP);
children.add(newState);

O uso do this como quarto parâmetro é o que salva o "Estado Pai", permitindo reconstruir toda a rota no final através do método printSolution.

O Algoritmo BFS (solveBFS):
O laço principal do explorador mantém a Fila de próximos passos e checa a tabela de visitados (HashSet).
Java

while (!queue.isEmpty()) {
    State currentState = queue.poll(); // Retira do início da Fila
    if(!visited.contains(currentState)){
        visited.add(currentState); // Poda de estados repetidos
        
        if (currentState.isGoal()) { // Teste de objetivo
            printSolution(currentState);
            return;
        }
    
        // Adiciona os filhos gerados ao FINAL da Fila
        List<State> children = currentState.getSuccessors();
        for (State child : children) {
            queue.add(child);
        }
    }
}

4. Resultados Obtidos

O algoritmo foi testado submetendo uma configuração inicial de tabuleiro específica. Graças à natureza do algoritmo de Busca em Largura (BFS), o sistema explorou as ramificações garantindo o caminho mais curto e retornou exatamente quais movimentos (UP, DOWN, LEFT, RIGHT) deveriam ser feitos.
Estado Inicial	Estado Objetivo (Goal)

[ 6 4 2 ]

[ 8 1 3 ]

[ 7 5 0 ]
	

[ 0 1 2 ]

[ 3 4 5 ]

[ 6 7 8 ]

Ao iniciar a execução, o algoritmo desenrola o caminho reconstruído, imprimindo o número total de passos (profundidade da árvore onde o objetivo foi encontrado) e exibindo o tabuleiro a cada transição no console.