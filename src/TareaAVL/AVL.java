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
public class AVL<T extends Comparable <T>>  extends ColaA<T> {
    private NodoAVL<T> raiz;
    private int cont;
    
    public AVL(){
        raiz=null;
        cont=0;
    }
    
    public NodoAVL<T> buscar(T elem){
        NodoAVL<T> actual=raiz;
        while(actual!=null && actual.element.compareTo(elem)!=0){
            if(actual.element.compareTo(elem)>0){
                actual=actual.izq;
            }
            else
                actual=actual.der;
        }
        
        return actual;
    }
    
    public boolean insertar(T elem){
        boolean res=false;
        if(elem!=null && buscar(elem)==null){
            cont++;
            NodoAVL<T> papa=null;
            NodoAVL<T> aux=raiz;
            if(raiz!=null){
                while(aux!=null){
                    papa=aux;
                    if(elem.compareTo(aux.element)>=0)
                        aux=aux.der;
                    else
                        aux=aux.izq;
                }

                aux=new NodoAVL<T>(elem);
                papa.cuelga(aux);

                boolean termine=false;
                while(!termine && aux.getPapa()!=null){
                    if(aux==aux.getPapa().getIzq())
                        aux.getPapa().factor-=1;
                    else
                        aux.getPapa().factor+=1;

                    if(aux.getPapa().factor==0)
                        termine=true;
                    if(Math.abs(aux.getPapa().getFactor())==2){
                        aux=rotacion(aux.getPapa());
                        termine=true;
                    }
                    aux=aux.getPapa();
                }
                res=true;
            }
            else{
                raiz=new NodoAVL<T>(elem);
                raiz.setPapa(null);
                res=true;
            }
        }
        return res;
    }
    
    public boolean elimina(T elem){
        boolean res=true;
        NodoAVL<T> actual= buscar(elem), padre;
        if(actual==null)
            res=false;
        else{
            cont--;
            boolean termine=false;
            if(actual.getDer()==null && actual.getIzq()==null){
                if(actual==raiz){
                    raiz=null;
                    termine=true;
                }
                else
                    if(elem.compareTo(actual.getPapa().getElement())<0){
                        if(actual.papa.factor==0)
                            termine=true;
                        actual.papa.factor+=1;
                        actual.papa.setIzq(null);
                        padre=actual.papa;
                    }
                    else{
                        if(actual.papa.factor==0)
                            termine=true;
                        actual.papa.factor-=1;
                        actual.papa.setDer(null);
                        padre=actual.papa;
                    }
            }
            else{
                if(actual.getIzq()==null){
                    if(actual==raiz){
                        raiz=actual.getDer();
                        raiz.setPapa(null);
                        termine=true;
                    }
                    else
                        actual.getPapa().cuelgaDer(actual.getDer());
                }
                else
                    if(actual.getDer()==null){
                        if(actual==raiz){
                            raiz=actual.getIzq();
                            raiz.setPapa(null);
                        }
                        else
                            actual.getPapa().cuelgaIzq(actual.getIzq());
                    }
                    else{
                        NodoAVL<T> suc= actual.getDer();
                        while(suc.getIzq()!=null)
                            suc=suc.getIzq();
                        actual.setElement(suc.getElement());
                        if(suc==actual.getDer()){
                            actual.cuelgaDer(suc.getDer());
                            if(actual.factor==0)
                                termine=true;
                            actual.factor-=1;
                        }
                        else{
                            if(suc.papa.factor==0)
                                termine=true;
                            suc.getPapa().factor+=1;
                            actual=suc.papa;
                            suc.getPapa().cuelgaIzq(suc.getDer());
                        }
                    }
            }
            if(actual!=null && Math.abs(actual.getFactor())==2){
                    actual=rotacion(actual);
                    termine=true;
            }
            while(!termine && actual.getPapa()!=null){
                if(actual==actual.getPapa().getIzq())
                    actual.getPapa().factor+=1;
                else
                    actual.getPapa().factor-=1;

                if(Math.abs(actual.getPapa().getFactor())==2){
                    actual=rotacion(actual.papa);
                    termine=true;
                }
                if(!termine && Math.abs(actual.getPapa().getFactor())==1){
                    termine=true;
                }
                actual=actual.getPapa();
            }
        }
        return res;
    }
    
    
    
    private NodoAVL<T> rotacion(NodoAVL<T> N){
        NodoAVL<T> res=N;
        
        if(N.getFactor()==-2 && N.izq!=null && N.izq.factor<=0){
            NodoAVL<T> alfa=N;
            NodoAVL<T> papa=N.getPapa();
            NodoAVL<T> beta=alfa.getIzq();
            NodoAVL<T> gamma=beta.getIzq();
            NodoAVL<T> A=gamma.getIzq();
            NodoAVL<T> B=gamma.getDer();
            NodoAVL<T> C=beta.getDer();
            NodoAVL<T> D=alfa.getDer();
            gamma.cuelgaIzq(A);
            gamma.cuelgaDer(B);
            alfa.cuelgaIzq(C);
            alfa.cuelgaDer(D);
            beta.cuelgaDer(alfa);
            beta.cuelgaIzq(gamma);
            if(papa!=null)
                papa.cuelga(beta);
            else{
                beta.setPapa(null);
                raiz=beta;
            }
            if(beta.factor==-1){
                if(gamma.factor==-1){
                    gamma.setFactor(-1);
                    beta.setFactor(0);
                    alfa.setFactor(0);
                }
                else{
                    if(gamma.factor==0){
                        gamma.setFactor(0);
                        beta.setFactor(0);
                        alfa.setFactor(0);
                    }
                    else{
                        gamma.setFactor(1);
                        beta.setFactor(0);
                        alfa.setFactor(0);
                    }  
                }
            }
            else{
                if(gamma.factor==-1){
                    gamma.setFactor(-1);
                    beta.setFactor(1);
                    alfa.setFactor(-1);
                }
                else{
                    if(gamma.factor==0){
                        gamma.setFactor(0);
                        beta.setFactor(1);
                        alfa.setFactor(-1);
                    }
                    else{
                        gamma.setFactor(1);
                        beta.setFactor(1);
                        alfa.setFactor(-1);
                    }
                }  
            }
            res=beta;
        }
        else{
            if(N.getFactor()==-2 && N.izq!=null &&  N.izq.factor==1){
                NodoAVL<T> alfa=N;
                NodoAVL<T> papa=N.getPapa();
                NodoAVL<T> beta=alfa.getIzq();
                NodoAVL<T> gamma=beta.getDer();
                NodoAVL<T> A=gamma.getIzq();
                NodoAVL<T> B=gamma.getDer();
                NodoAVL<T> C=beta.getIzq();
                NodoAVL<T> D=alfa.getDer();
                gamma.cuelgaIzq(beta);
                gamma.cuelgaDer(alfa);
                alfa.cuelgaDer(C);
                alfa.cuelgaIzq(B);
                beta.cuelgaDer(A);
                beta.cuelgaIzq(C);
                if(papa!=null)
                    papa.cuelga(gamma);
                else{
                    gamma.setPapa(null);
                    raiz=gamma;
                }
                if(gamma.getFactor()==1){
                    beta.setFactor(-1);
                    alfa.setFactor(0);
                    gamma.setFactor(0);
                }
                else{
                    if(gamma.getFactor()==-1){
                        beta.setFactor(0);
                        alfa.setFactor(1);
                        gamma.setFactor(0);
                    }
                    else{
                        beta.setFactor(0);
                        alfa.setFactor(0);
                        gamma.setFactor(0);
                    }
                }
                res=gamma;
            }
            else{
                if(N.getFactor()==2 && N.der!=null &&  N.der.factor>=0){
                    
                    NodoAVL<T> alfa=N;
                    NodoAVL<T> papa=N.getPapa();
                    NodoAVL<T> beta=alfa.getDer();
                    NodoAVL<T> gamma=beta.getDer();
                    NodoAVL<T> A=gamma.getIzq();
                    NodoAVL<T> B=gamma.getDer();
                    NodoAVL<T> C=beta.getIzq();
                    NodoAVL<T> D=alfa.getIzq();
                    gamma.cuelgaIzq(A);
                    gamma.cuelgaDer(B);
                    alfa.cuelgaDer(C);
                    alfa.cuelgaIzq(D);
                    beta.cuelgaIzq(alfa);
                    beta.cuelgaDer(gamma);
                    if(papa!=null)
                        papa.cuelga(beta);
                    else{
                        beta.setPapa(null);
                        raiz=beta;
                    }
                    if(beta.factor==1){
                        if(gamma.factor==1){
                            gamma.setFactor(1);
                            beta.setFactor(0);
                            alfa.setFactor(0);
                        }
                        else{
                            if(gamma.factor==0){
                                gamma.setFactor(0);
                                beta.setFactor(0);
                                alfa.setFactor(0);
                            }
                            else{
                                gamma.setFactor(-1);
                                beta.setFactor(0);
                                alfa.setFactor(0);
                            }  
                        }
                    }
                    else{
                        if(gamma.factor==1){
                            gamma.setFactor(-1);
                            beta.setFactor(-1);
                            alfa.setFactor(1);
                        }
                        else{
                            if(gamma.factor==0){
                                gamma.setFactor(0);
                                beta.setFactor(-1);
                                alfa.setFactor(1);
                            }
                            else{
                                gamma.setFactor(1);
                                beta.setFactor(-1);
                                alfa.setFactor(1);
                            }
                        }  
                    }
                    res=beta;
                }
                else{
                    NodoAVL<T> alfa=N;
                    NodoAVL<T> papa=N.getPapa();
                    NodoAVL<T> beta=alfa.getDer();
                    NodoAVL<T> gamma=beta.getIzq();
                    NodoAVL<T> A=gamma.getIzq();
                    NodoAVL<T> B=gamma.getDer();
                    NodoAVL<T> D=beta.getDer();
                    NodoAVL<T> C=alfa.getIzq();
                    alfa.cuelgaIzq(C);
                    alfa.cuelgaDer(A);
                    beta.cuelgaDer(D);
                    beta.cuelgaIzq(B);
                    gamma.cuelgaDer(beta);
                    gamma.cuelgaIzq(alfa);
                    
                    if(papa!=null)
                        papa.cuelga(gamma);
                    else{
                        gamma.setPapa(null);
                        raiz=gamma;
                    }
                    
                    if(gamma.getFactor()==1){
                        beta.setFactor(-1);
                        alfa.setFactor(0);
                        gamma.setFactor(0);
                    }
                    else{
                        if(gamma.getFactor()==-1){
                            beta.setFactor(0);
                            alfa.setFactor(1);
                            gamma.setFactor(0);
                        }
                        else{
                            beta.setFactor(0);
                            alfa.setFactor(0);
                            gamma.setFactor(0);
                        }
                    }
                    
                    res=gamma;
                    
                }
                    
            }
                
        }
        return res;
    }
            
    public String imprimir(){
        StringBuilder cad=new StringBuilder();
        cad.append("\n");
        String res="";
        ColaA cola=new ColaA(cont);
        if(raiz!=null){
            cola.agrega(raiz);
            res=imprimir(cad, cola);
        }
        return res;
    }

    private String imprimir(StringBuilder cad, ColaA cola){
        if(cola.estaVacia()){
            return cad.toString();
        }
        
        NodoAVL<T> actual =(NodoAVL<T>)cola.quita();
        cad.append("∞ "+actual.element).append(" FE:").append(actual.factor+" ");
        
        
        if(actual.izq!=null){
            cola.agrega(actual.izq);
        }
        
        if(actual.der!=null)
            cola.agrega(actual.der);
        
        return imprimir(cad, cola);
    }
    
    public static void main(String args[]){
        
        AVL ar=new AVL();
        System.out.println(ar.insertar(100));
        System.out.println(ar.imprimir());
        ar.insertar(300);
        System.out.println(ar.imprimir());
        System.out.println(ar.insertar(400));
        System.out.println(ar.imprimir());
        ar.insertar(350);
        System.out.println(ar.imprimir());
        ar.insertar(375);
        System.out.println(ar.imprimir());
        ar.insertar(50);
        System.out.println(ar.imprimir());
        ar.insertar(200);
        System.out.println(ar.imprimir());
        ar.insertar(360);
        System.out.println(ar.imprimir());
        ar.insertar(380);
        System.out.println(ar.imprimir());
        ar.insertar(500);
        System.out.println(ar.imprimir());

        

        ar.insertar(390);
        System.out.println(ar.imprimir());
        ar.elimina(375);
        System.out.println(ar.imprimir());
        ar.elimina(50);
        System.out.println(ar.imprimir());
        ar.elimina(400);
        System.out.println(ar.imprimir());
        
        System.out.println(ar.elimina(380));
        
        System.out.println(ar.imprimir());
//        
//        System.out.println(ar.elimina(9));
//        
//        System.out.println(ar.imprimir());
//        System.out.println(ar.buscar(10));
//        
//        System.out.println(ar.elimina(9));
//        
//        System.out.println(ar.imprimir());
//        
//        System.out.println(ar.elimina(6));
//        
//        System.out.println(ar.imprimir());
        

    }
    
    
}
