/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

/**
 *
 * @author tonotoledo
 */
public class NodoAVL<T extends Comparable <T>> {
    int factor;
    NodoAVL<T> izq;
    NodoAVL<T> der;
    NodoAVL<T> papa;
    T element;
    
    NodoAVL(T e){
        element=e;
        izq=null;
        der=null;
        factor=0;    
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }
    
    public void cuelga(NodoAVL<T> n){
        
        if(n==null)
            return;
        if(n.element.compareTo(element)<0)
            izq=n;
        else
            der=n;
        n.setPapa(this);
        
    }
    
    public void cuelgaIzq(NodoAVL<T> n){
        if(n!=null)
            n.setPapa(this);
        izq=n;
    }
    
    public void cuelgaDer(NodoAVL<T> n){
        if(n!=null)
            n.setPapa(this);
        der=n;
    }
    
    
    
    
}
