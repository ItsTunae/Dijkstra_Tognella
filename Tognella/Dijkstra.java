import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    // Classe Nodo che rappresenta un nodo del grafo
    public static class Nodo implements Comparable<Nodo> {
        public final int id; // identificatore del nodo
        public List<Vicino> vicini; // lista di nodi vicini
        public double distanza; // distanza minima dal nodo sorgente
        public Nodo precedente; // nodo precedente nella sequenza dei nodi visitati

        public Nodo(int id) {
            this.id = id;
            this.vicini = new ArrayList<>();
            this.distanza = Double.POSITIVE_INFINITY;
            this.precedente = null;
        }

        // Aggiunge un nodo vicino con un certo peso
        public void aggiungiVicino(Nodo vicino, double peso) {
            this.vicini.add(new Vicino(vicino, peso));
        }

        // Confronta i nodi per distanza
        @Override
        public int compareTo(Nodo altro) {
            return Double.compare(this.distanza, altro.distanza);
        }

        // Classe Vicino che rappresenta un nodo vicino con il peso dell'arco
        public static class Vicino {
            public final Nodo nodo;
            public final double peso;

            public Vicino(Nodo nodo, double peso) {
                this.nodo = nodo;
                this.peso = peso;
            }
        }
    }

    // Calcola il cammino minimo da un nodo sorgente a tutti gli altri nodi del grafo
    public static void dijkstra(Nodo[] grafo, Nodo sorgente) {
        sorgente.distanza = 0;

        PriorityQueue<Nodo> codaPriorita = new PriorityQueue<>();
        codaPriorita.add(sorgente);

        while (!codaPriorita.isEmpty()) {
            Nodo nodo = codaPriorita.poll();

            for (Nodo.Vicino vicino : nodo.vicini) {
                double nuovaDistanza = nodo.distanza + vicino.peso;
                if (nuovaDistanza < vicino.nodo.distanza) {
                    codaPriorita.remove(vicino.nodo);
                    vicino.nodo.distanza = nuovaDistanza;
                    vicino.nodo.precedente = nodo;
                    codaPriorita.add(vicino.nodo);
                }
            }
        }
    }
    public static void stampaNodi(Nodo[] grafo, Nodo sorgente) {
        System.out.println("Nodi del grafo in ordine di distanza dal nodo sorgente:");
        for (Nodo nodo : grafo) {
            //Controllo dell'input : se il valore del nodo è "Infinito", quindi non esistente, non faccio stampare nulla
            if(nodo.distanza != Double.POSITIVE_INFINITY)
                System.out.printf("Nodo %d, distanza: %.1f\n", nodo.id, nodo.distanza);
        }
    }
    // Esegue un test dell'algoritmo su un grafo di esempio
    public static void main(String[] args) {
        // Creazione del grafo di esempio
        Nodo[] grafo = new Nodo[11];
        for (int i = 0; i < grafo.length; i++) {
            grafo[i] = new Nodo(i);
        }
        //Assegno ai singoli nodi del grafo il loro identificativo e il peso del collegamento da quel nodo all'altro
        grafo[0].aggiungiVicino(grafo[2], 2);
        grafo[0].aggiungiVicino(grafo[6], 8);

        grafo[2].aggiungiVicino(grafo[0], 2);
        grafo[2].aggiungiVicino(grafo[4], 2);
        grafo[2].aggiungiVicino(grafo[8], 6);

        grafo[4].aggiungiVicino(grafo[2], 2);
        grafo[4].aggiungiVicino(grafo[6], 2);
        grafo[4].aggiungiVicino(grafo[9], 9);

        grafo[6].aggiungiVicino(grafo[0], 8);
        grafo[6].aggiungiVicino(grafo[4], 2);
        grafo[6].aggiungiVicino(grafo[9], 3);

        grafo[8].aggiungiVicino(grafo[2], 6);
        grafo[8].aggiungiVicino(grafo[10], 5);

        grafo[9].aggiungiVicino(grafo[4], 9);
        grafo[9].aggiungiVicino(grafo[6], 3);
        grafo[9].aggiungiVicino(grafo[10], 1);

        // Esecuzione dell'algoritmo di Dijkstra
        dijkstra(grafo, grafo[0]);

        // Stampa dei nodi del grafo in ordine di distanza dal nodo sorgente
        stampaNodi(grafo, grafo[0]);
    }
}