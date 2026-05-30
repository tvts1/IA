1. Formulação do Problema

O problema consiste em posicionar 8 rainhas em um tabuleiro de xadrez (8x8) sem que nenhuma delas se ataque. Para otimização de memória, o tabuleiro não foi formulado como uma matriz tradicional, mas sim como uma Lista de inteiros (List<Integer>).

Nessa formulação:

    O índice da lista representa a linha do tabuleiro.

    O valor armazenado no índice representa a coluna onde a rainha está posicionada.

    Estado Inicial: Pode ser o tabuleiro vazio ou uma lista já contendo posições pré-definidas (ex: [0, 4] para rainhas em (0,0) e (1,4)).

    Teste de Objetivo: O problema é considerado resolvido quando o tamanho da lista atinge 8 elementos (8 rainhas posicionadas em 8 linhas).

    Função Sucessora: Consiste em pegar o estado atual, calcular a próxima linha disponível e tentar inserir uma nova rainha nas colunas de 0 a 7, filtrando pelas posições seguras.

2. Técnica Utilizada

O algoritmo implementado foi a Busca em Profundidade (DFS).
Para aplicar essa técnica seguindo o pseudocódigo de referência, utilizei a estrutura de dados Pilha (Stack em Java). A Pilha garante o comportamento LIFO (Last In, First Out): os últimos estados gerados (filhos) são colocados no topo e testados imediatamente na próxima iteração do laço. Isso força o algoritmo a descer o mais fundo possível na árvore de busca (avançando as linhas do tabuleiro) antes de retroceder (fazer backtracking) para tentar colunas alternativas.

Além disso, para evitar o reprocessamento de configurações repetidas (poda), implementei uma tabela de dispersão (HashSet), que armazena os estados já visitados e barra a reavaliação de tabuleiros idênticos.

3. Principais Trechos do Código

A arquitetura do código foi dividida em responsabilidades claras. Os dois trechos principais são o método de validação ("isSafe") e o laço de busca.

O método de validação (isSafe):
A verificação de ataques foi feita de forma puramente matemática. Como já garantimos que não há duas rainhas na mesma linha (pela estrutura da Lista), o método precisa apenas checar colunas e diagonais contra as rainhas anteriores.
Java

if (col == newCol || Math.abs(col - newCol) == Math.abs(row - newRow)) {
    return false;
}

A sacada lógica aqui foi utilizar o valor absoluto (Math.abs). Se a diferença de passos entre as linhas for igual à diferença de passos entre as colunas, as peças formam um quadrado perfeito e estão na mesma diagonal.

O laço de Busca em Profundidade (solveDFS):
O núcleo do algoritmo é mantido por um laço do-while que manipula a Pilha e o HashSet.
Java

do {
    List<Integer> currentState = frontierStack.pop(); 
    if (!visitedStates.contains(currentState)) { 
        visitedStates.add(currentState);         
        
        if (currentState.size() == 8) {   
            isFound = true;
            solution = currentState;
            break; 
        } else {
            // ... (Gera cópias do estado atual com a nova rainha) ...
            // Empilha os novos filhos gerados
            for (List<Integer> child : children) {
                frontierStack.push(child);
            }
        }
    }
} while (!isFound && !frontierStack.isEmpty());

Este trecho mostra exatamente onde ocorre a poda (o if checando o visitedStates) e a alimentação da DFS: ao invés de usar recursão, nós enchemos a Pilha (push) com os nós filhos gerados. A ausência de uma ordenação por custo garante que é uma DFS pura.

4. Resultados Obtidos

O algoritmo foi testado submetendo quatro cenários iniciais diferentes. O algoritmo obteve sucesso em todos, e os resultados provam como fornecer um estado inicial mais restrito diminui drasticamente a quantidade de nós criados na memória (espaço de busca menor).
Cenário Inicial	Representação na Lista	Solução Encontrada (Colunas por Linha)	Nós Gerados na Árvore
Tabuleiro Vazio	[]	[7, 3, 0, 2, 5, 1, 6, 4]	114
Com 1 Rainha	[0]	[0, 6, 3, 5, 7, 1, 4, 2]	134
Com 2 Rainhas	[0, 4]	[0, 4, 7, 5, 2, 6, 1, 3]	19
Com 3 Rainhas	[0, 4, 7]	[0, 4, 7, 5, 2, 6, 1, 3]	14

(Obs: O cenário com 1 rainha gerou mais nós que o tabuleiro vazio nesta execução específica porque a posição inicial obrigatória em (0,0) forçou o algoritmo a explorar muitos becos sem saída antes de encontrar uma ramificação válida).
