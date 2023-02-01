import java.util.LinkedList;

public class Nomes {
    public static void orderedInsert(String n, LinkedList<String> lista) {
        int i=0;
        while(i<lista.size() && lista.get(i).compareTo(n)<0){
            i++;
        }
        lista.add(i,n);
    }
    public static void main(String[] args) {
        LinkedList <String> nomes = new LinkedList<>();

        for(String n: args){
            orderedInsert(n,nomes);
        }

        System.out.println("Nomes ordenados: "+ nomes);
    }
}
