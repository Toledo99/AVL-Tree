/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;
import java.util.ArrayList;

/**
 *
 * @author huesos
 */
public interface ColaADT <T>{
    
    public boolean estaVacia();
    public T consultaPrimero();
    public T quita();
    public void agrega(T dato);
    
    public int cuentaElementos();
    public T consultaUltimo();
    public ArrayList<T> multiQuita (int n);
}
