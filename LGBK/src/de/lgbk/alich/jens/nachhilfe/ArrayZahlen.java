package de.lgbk.alich.jens.nachhilfe;


/**
 * Write a description of class ArrayZahlen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArrayZahlen
{
    // instance variables - replace the example below with your own
    private int[] zahlen;

    public static void main(String[] ars) {
        System.out.println("Start:");
        ArrayZahlen array = new ArrayZahlen();
        array.quicksort();
        System.out.println();
        array = new ArrayZahlen();
        array.quicksort();
        System.out.println();
        array = new ArrayZahlen();
        array.quicksort();
        System.out.println();
        array = new ArrayZahlen();
        array.quicksort();
        System.out.println();
        array = new ArrayZahlen();
        array.quicksort();
        System.out.println("Ende");
    }

    /**
     * Constructor for objects of class ArrayZahlen
     */
    public ArrayZahlen(int laenge)
    {
        zahlen = new int[laenge];
        fuellen();
        ausgeben();
    }
    
    public ArrayZahlen()
    {
        zahlen = new int[10];
        fuellen();
        ausgeben();
    }
    
    public void fuellen(int maxWert)
    {
        java.util.Random random = new java.util.Random();
        for (int i=0; i<zahlen.length; i++)
            zahlen[i] = random.nextInt(maxWert)+1;
    }
    
    public void fuellen() {
        fuellen(100);
    }

    public void ausgeben() {
        System.out.print("[ ");
        for (int i=0; i<zahlen.length; i++)
        {
            System.out.print(zahlen[i]+"  ");
        }
        System.out.println("]");
    }
    
    public void mergesort() {
        sort(0, zahlen.length-1);
        ausgeben();
    }
    
    public void sort(int links, int rechts) {
        if (links >= rechts) return;
        int mitte = (links + rechts)/2;
        sort(links, mitte);
        sort(mitte + 1, rechts);
        merge(links, mitte, rechts);
    }
    
    public void merge(int links, int mitte, int rechts) {
        int[] hilfsarray = new int[zahlen.length];
        for (int i = links; i <= mitte; i++) {
            hilfsarray[i] = zahlen[i];
        }
        
        for (int j = mitte + 1; j <= rechts; j++) {
            hilfsarray[rechts + mitte + 1 - j] = zahlen[j];
        }
        
        int zeigerLinks = links;
        int zeigerRechts = rechts;
        
        for (int k = links; k <= rechts; k++) {
            if (hilfsarray[zeigerLinks] <= hilfsarray[zeigerRechts]) {
                zahlen[k] = hilfsarray[zeigerLinks];
                zeigerLinks++;
            } else {
                zahlen[k] = hilfsarray[zeigerRechts];
                zeigerRechts--;
            }
        }
    }
    
    public void quicksort() {
        pivot(0, zahlen.length-1);
        ausgeben();
    }
    
    private void pivot(int links, int rechts) {
        if (links < rechts) {
            int pivot = links;
            for (int i = pivot + 1; i <= rechts; i++) {
                if (zahlen[i] <= zahlen[pivot]) {
                    tauschen(pivot, i);
                    tauschen(pivot + 1, i);
                    pivot++;
                }
            }
            pivot(links, pivot - 1);
            pivot(pivot + 1, rechts);
        }
    }
    
    public void tauschen(int zahlEins, int zahlZwei) {
        int speicher = zahlen[zahlEins];
        zahlen[zahlEins] = zahlen[zahlZwei];
        zahlen[zahlZwei] = speicher;
    }
}
