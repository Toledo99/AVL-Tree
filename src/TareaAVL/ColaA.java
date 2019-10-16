/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

//import PILA.EmptyCollectionException;
import java.util.ArrayList;

//queue

public class ColaA <T> implements ColaADT <T> {
    private T[] cola;
    private int frente;
    private int fin;
    private final int MAX = 3;
    
    public ColaA(){
        cola = (T[]) new Object[MAX];
        frente = -1;
        fin = -1;
    }
    
    public ColaA(int max){
        cola = (T[]) new Object[max];
        frente = -1;
        fin = -1;
    }
    
    public boolean estaVacia(){
        return frente == -1;
    }
    
    public T consultaPrimero(){
        if(estaVacia())
            throw new NullPointerException();
        return cola[frente];
    }
    
    public void agrega(T dato){
        
        if(frente == -1)
            frente = 0;
        else
            if( (fin+1) % cola.length == frente )  // es lo mismo que:" if(frente==0 && fin==cola.length-1 || (fin+1==frente)
                expande();
        
        fin = (fin+1) % cola.length;
        cola[fin] = dato;
        
    }
    
    
    private void expande(){
        
        int n = cola.length;
        
        T[] nuevo = (T[]) new Object [n * 2];
        
        for(int i=0; i<n ; i++){
            nuevo[i] = cola[ (frente+i) % n ] ;
        }
        
        cola = nuevo;
        frente = 0;
        fin = (n-1)%cola.length;
    }
    
    @Override
    public T quita(){
        if(estaVacia())
            throw new EmptyCollectionException();
        T res = cola[frente];
        cola[frente]=null;
        
        if(frente == fin){
            frente=-1;
            fin=-1;
        }
        else
            frente = (frente+1) % cola.length;
        return res;
    }

   
    @Override
    public int cuentaElementos() {
        if(estaVacia())
           return 0;
        else
            if(frente <= fin)
                return fin - frente +1 ; 
            else
                return cola.length-frente+fin+1;
    }

  
    @Override
    public T consultaUltimo() {
        if(estaVacia())
            throw new NullPointerException();
        else
            return cola[fin];
    }

    
    public ArrayList<T> multiQuita(int n) {
        if(estaVacia())
            throw new NullPointerException();
        ArrayList <T> aux;
        if(cuentaElementos() >= n){
            aux = new ArrayList();
            for(int i=0; i<n; i++)
                aux.add(quita());
        }else
            aux=null;
        
        return aux;
    }
    
    
    public String toStringComoArreglo(){
        String res = "";
        
        for(int i=0; i<cola.length; i++)
            res += cola[i];
        
        return res;
    }
    
    public String toStringComoFila(){
        String res = "";
        for(int i = 0; i< this.cuentaElementos(); i++)
            res += cola[ (frente+i)%cola.length];
        return res;
    }
            
    public static void main (String [] args){
        ColaA col = new ColaA();
        col.agrega("A"); //0
        col.agrega("B"); //1
        col.agrega("C"); //2
        System.out.println(col.toStringComoArreglo());
        col.quita();
        col.agrega("D"); //3
        System.out.println(col.toStringComoArreglo());
        System.out.println(col.toStringComoFila());
       
        System.out.println(col.consultaPrimero());
        System.out.println(col.consultaUltimo());
        System.out.println(""+col.cuentaElementos());
    }
}
