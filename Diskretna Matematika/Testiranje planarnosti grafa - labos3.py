import itertools  
import numpy as np  


def redoslijed_grafa(adj_matrix):
    if len(adj_matrix) != len(adj_matrix[0]):  
        return -1
    else:
        return len(adj_matrix)  

def stupnjevi_vrhova(adj_matrix):
    degree_sequence = []
    for vertex in range(len(adj_matrix)):
        degree_sequence.append(sum(adj_matrix[vertex])) 
    return degree_sequence

def pronadi_podgrafove(adj_matrix):
    all_adj_matrix = []  
    idx = list(range(len(adj_matrix))) 
    possible_idx_combinations = [list(i) for i in itertools.permutations(idx, len(idx))]  
    for idx_comb in possible_idx_combinations:
        a = adj_matrix
        a = a[idx_comb]  
        a = np.transpose(np.transpose(a)[idx_comb]) 
        all_adj_matrix.append({"perm_vertex": idx_comb, "adj_matrix": a})

    return all_adj_matrix

def testiraj_izomorfnost(adj_1, adj_2):
    degree_sequence_1 = stupnjevi_vrhova(adj_1).sort(reverse=True) 
    degree_sequence_2 = stupnjevi_vrhova(adj_2).sort(reverse=True)
    if redoslijed_grafa(adj_1) != redoslijed_grafa(adj_1): 
        return False
    elif np.array_equal(degree_sequence_1, degree_sequence_2) == False: 
        return False
    else:
        for adj_matrix in list(
            map(lambda matrix: matrix["adj_matrix"], pronadi_podgrafove(adj_2))
        ):
            if np.array_equal(adj_1, adj_matrix) == True:
                return True
    return False

def izgladi_matricu(adj_matrix):
    adj_matrix = adj_matrix.copy()
    while True:
        degree_sequence = stupnjevi_vrhova(adj_matrix) 
        if degree_sequence.count(2) == 0: 
            break
        else:
            for vertex in range(len(adj_matrix)):
                if degree_sequence[vertex] == 2: 
                    neighbors = []
                    for i in range(len(adj_matrix)):
                        if adj_matrix[vertex][i] == 1:
                            neighbors.append(i)
                    adj_matrix[neighbors[0]][neighbors[1]] = 1  
                    adj_matrix[neighbors[1]][neighbors[0]] = 1
                    adj_matrix = np.delete(adj_matrix, vertex, 0) 
                    adj_matrix = np.delete(adj_matrix, vertex, 1) 
                    break
    return adj_matrix

def inducirani_podgrafovi(adj_matrix):
    adj_matrix = adj_matrix.copy()
    induced_subgraphs = []  
    for i in range(1, 2 ** len(adj_matrix)):
        included_vertices = []
        for j in range(len(adj_matrix)):
            if (i >> j) & 1:
                included_vertices.append(j) 
        induced_subgraph = np.take(
            np.take(adj_matrix, included_vertices, 0), included_vertices, 1
        ) 
        if redoslijed_grafa(induced_subgraph) < 5: 
            continue
        if sum(stupnjevi_vrhova(induced_subgraph)) < 18:
            continue
        induced_subgraphs.append(induced_subgraph)

    return induced_subgraphs

def bridovski_podgrafovi(adj_matrix):
    adj_matrix = adj_matrix.copy()
    edge_subgraphs = []  
    edges = []
    for i in range(len(adj_matrix)):
        for j in range(i):
            if adj_matrix[i][j] == 1:
                edges.append((i, j)) 
    for i in range(2 ** len(edges)):
        if bin(i).count("1") < 9:
            continue
        included_edges = []
        for j in range(len(edges)):
            if (i >> j) & 1:
                included_edges.append(edges[j]) 
        edge_subgraph = np.zeros((len(adj_matrix), len(adj_matrix)))
        for edge in included_edges:
            edge_subgraph[edge[0]][edge[1]] = 1
            edge_subgraph[edge[1]][edge[0]] = 1
        if min(stupnjevi_vrhova(edge_subgraph)) < 2: 
            continue
        edge_subgraphs.append(edge_subgraph)

    return edge_subgraphs

def testiraj_homoeomorfnost(adj_matrix, subgraph):
    adj_matrix = adj_matrix.copy()
    subgraph = subgraph.copy()
    induced_subgraphs = inducirani_podgrafovi(adj_matrix)
    for induced_subgraph in induced_subgraphs:
        edge_subgraphs = bridovski_podgrafovi(induced_subgraph)
        for edge_subgraph in edge_subgraphs:
            if testiraj_izomorfnost(
                izgladi_matricu(edge_subgraph), izgladi_matricu(subgraph)
            ):
                return True
    return False

def testiraj_planarnost(adj_matrix):
    if testiraj_homoeomorfnost(adj_matrix, K33):
        print("Graf nije planaran, ima podgraf homeomorfan s K_3,3")
    elif testiraj_homoeomorfnost(adj_matrix, K5):
        print("Graf nije planaran, ima podgraf homeomorfan s K_5")
    else:
        print("Graf je planaran")

K33 = np.array(
    [
        [0, 0, 0, 1, 1, 1],
        [0, 0, 0, 1, 1, 1],
        [0, 0, 0, 1, 1, 1],
        [1, 1, 1, 0, 0, 0],
        [1, 1, 1, 0, 0, 0],
        [1, 1, 1, 0, 0, 0],
    ]
)

K5 = np.array(
    [
        [0, 1, 1, 1, 1],
        [1, 0, 1, 1, 1],
        [1, 1, 0, 1, 1],
        [1, 1, 1, 0, 1],
        [1, 1, 1, 1, 0],
    ]
)


filename = input("Unesite ime datoteke: ")

try:
    adj_matrix = np.loadtxt(filename, skiprows=1)
except FileNotFoundError:
    print(f"Datoteka nije pronađena.")
    exit()
testiraj_planarnost(adj_matrix)
