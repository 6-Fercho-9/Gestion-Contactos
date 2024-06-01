/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Lenovo
 */
public class ArbolB<K extends Comparable<K>,V> extends ArbolMViasBusqueda<K,V>implements Serializable{
    private final int nroMaxDeClaves;
    private final int nroMaxDeHijos;
    private final int nroMinDeClaves;
    private final int nroMinDeHijos;
    public ArbolB(){
        super();
        this.nroMaxDeHijos=3;
        this.nroMaxDeClaves=2;
        this.nroMinDeClaves=1;
        this.nroMinDeHijos=2;
    }
    public ArbolB(int orden) throws OrdenInvalidoException{
        super(orden);
        this.nroMaxDeHijos=super.orden;
        this.nroMaxDeClaves=super.orden-1;
        this.nroMinDeClaves=this.nroMaxDeClaves/2;
        this.nroMinDeHijos=this.nroMinDeClaves+1;
    }
    //@Override
    public void insertarClaveValorIterativo(K clave, V valor)  {
      if(this.esArbolVacio()){
          this.raiz=new NodoMVias<>(this.orden+1,clave,valor);//lo creo con un orden de exceso
      }else{
          //primero verifico si el elemento no esta ya insertado
          NodoMVias<K,V>  nodoAuxiliar=this.raiz;
          Stack<NodoMVias<K,V>> pilaAncestros=new Stack<>();
          do{
            int posicionClaveAInsertar=super.buscarClaveEnNodo(clave,nodoAuxiliar);
            //si la posicion es invalida quiere decir que la clave a insertar esta en el nodoactual
            if(posicionClaveAInsertar!=ArbolMViasBusqueda.POSICION_INVALIDA){
                //si es asi entonces,solo actualiza el valor,aca entra si encontro la clave y solo actualiza el valor
                nodoAuxiliar.setValor(posicionClaveAInsertar, valor);
                nodoAuxiliar=NodoMVias.nodoVacio();//para que acabe el bucle
            }else if(nodoAuxiliar.esHoja()){
                
                    //este metodo se encargar de insertar en el nodo de manera ordenada
                    super.insertarClaveEnNodo(clave, valor, nodoAuxiliar);
                    if(nodoAuxiliar.nroDeClavesNoVacias()>this.nroMaxDeClaves){
                        this.dividirNodo(nodoAuxiliar,pilaAncestros);//aca crece para arriba
                    }
                    nodoAuxiliar=NodoMVias.nodoVacio();//finaliza
            }else{
                    //si no es hoja entonces tengo que ver por donde bajar
                    int posicionParabajar=super.posicionhijoparaSetear(nodoAuxiliar,clave);
                    pilaAncestros.push(nodoAuxiliar);//registro el camino
                    nodoAuxiliar=nodoAuxiliar.getHijo(posicionParabajar);
                    
            }
            
          }while(!NodoMVias.esNodoVacio(nodoAuxiliar));
      }
       
    }
    private void dividirNodo(NodoMVias<K,V> nodo,Stack<NodoMVias<K,V>> pila){
        
    }
 /*   public V eliminar(K claveAEliminar){
        if(claveAEliminar==null){
            throw new IllegalArgumentException("error.. clave vacia");
            
        }
        Stack<NodoMVias<K,V>> pilaAncestros=new Stack<>();
        NodoMVias<K,V> nodoDeLaClaveAEliminar=this.buscarNodoDeLaClave(claveAEliminar,pilaAncestros);
        if(NodoMVias.esNodoVacio(nodoDeLaClaveAEliminar)){//si no lo encontro
            return null;
        }
        int posicionClaveAEliminar=super.buscarPosicionDeClave(claveAEliminar,nodoDeLaClaveAEliminar);
        V valorARetornar=nodoDeLaClaveAEliminar.getClave(posicionClaveAEliminar);
        if(nodoDeLaClaveAEliminar.esHoja()){
            super.eliminarClaveDeNodoDePosicion(nodoDeLaClaveAEliminar,posicionDeClaveAEliminar);
            if(nodoDeLaClaveAEliminar.nroDeClavesNoVacias()<this.nroMinDeClaves){
                if(pilaAncestros.isEmpty()){//aca es cuando es la raiz
                    if(nodoDeLaClaveAEliminar.nroDeClavesNoVacias()==0){
                        super.vaciar();
                    }
                }else{
                    prestarOfusionar(nodoDeLaClaveAEliminar,pilaAncestros)
                }
            }
        }else{
            pilaAncestros.push(nodoDeLaClaveAEliminar);
            NodoMVias<K,V> nodoDelPredecesor=this.obtenerNodoDelPredecesor(pilaAncestros,nodoDeLaClaveAEliminar.getHijo(posicionClaveAEliminar));
            K claveDelPredecesor=nodoDelPredecesor.getClave(nodoDelPredecesor.nroDeClavesNoVacias()-1);
            V valorDelPredecesor=nodoDelPredecesor.getValor(nodoDelPredecesor.nroDeClavesNoVacias()-1);
            super.eliminarClaveDelNodoDePosicion(nodoDelPredecesor,nodoDelPredecesor.nroDeClavesNoVacias()-1);
            nodoDeLaClaveAEliminar.setClave(posicionClaveAEliminar, claveDePredecesor);
            nodoDeLaClaveAEliminar.setValor(posicionClaveAEliminar, claveDePredecesor);
            if(nodoDelPredecesor.nroDeClavesNoVacias()<this.nroMinDeClaves){
                prestarOfusionar(nodoDelPredecesor,pilaDeAncestros);
            }
            return valorAretornar;
        }
    }*/
    /*
    mi insertar recursivo inicialmente se inserta en la hoja sin importar si pasa del nromax de claves no vacias
    entonces tengo que dividir la raiz por si no cumpliese la regla dado que el insertar recursivo,divide pero cuando esta en desapilacion
    pero cuando esta en el primer caso base no llega dividir a ese nodo,entonces eso lo hago yo manualmente
    asumiendo que faltase dividir la raiz,le paso un nodoPadre nulo y el nodo hijo sera el nodo donde se inserto la clave
    
    */
    @Override
    public void insertarClaveValor(K claveAInsertar,V valorAInsertar){
     //   this.raiz=this.dividirNodo(null, this.insertarRecursivo(this.raiz,claveAInsertar, valorAInsertar));
        NodoMVias<K,V> nodoHijo=this.insertarRecursivo(this.raiz,claveAInsertar, valorAInsertar);
        this.raiz=this.dividirNodo(null, nodoHijo);
        
    }
    private NodoMVias<K,V> insertarRecursivo(NodoMVias<K,V> nodoActual,K claveAInsertar,V valorAInsertar){
        //aca solo entra la primera vez,si es arbol vacio
        if(NodoMVias.esNodoVacio(nodoActual)){
        
            return new NodoMVias<K,V>(this.orden+1,claveAInsertar,valorAInsertar);
        }
        //si no
        if(nodoActual.esHoja()){
            //inserto de manera ordenada
            int posicionClaveAInsertar=super.buscarClaveEnNodo(claveAInsertar, nodoActual);
            if(posicionClaveAInsertar!=super.POSICION_INVALIDA){//si la posicion es valida quiere decir que la clave a insertar ya existe ene l nodo,entonces solo actualiza el valor
                nodoActual.setValor(posicionClaveAInsertar, valorAInsertar);
                return nodoActual;
            }
            //aca lo inserta de manera ordenada
            super.insertarClaveEnNodo(claveAInsertar, valorAInsertar, nodoActual);
            return nodoActual;
        }
        
        //si no es hoja,tengo que ver por donde bajar
        int posicionDondeBajar=super.posicionhijoparaSetear(nodoActual, claveAInsertar);
        NodoMVias<K,V> supuestoHijo=this.insertarRecursivo(nodoActual.getHijo(posicionDondeBajar), claveAInsertar, valorAInsertar);
        return this.dividirNodo(nodoActual, supuestoHijo);
    }
    /*
    este metodo tiene 2 partes,una es por si es la raiz es decir que si el nodoPadre(parametro) es nulo,entonces estamos en la raiz
    obviamente el nodoHijo simepre tendra un valor,y el nodoHijo sera el que no cumpla con la regla,es decir 
    este generalmente tendra un numero de claves mayor al maximo
    pero siempre se pregunta porque puede haber casos donde cumpla con la regla
    
    entonces primero creo un supuesto nodo padre en base al nodoHijo 
    y tambien creo un supuesto hijo izquierdo y derecho,donde estos ya tendran acceso a sus posibles hijos que tendian antes
    
    generalmente cuando el nodopadre es null cuando hace el seteo siempre lo hace en la posicion 0 y 1
    
    
    el otro caso es si el padre es no nulo,entonces quiere decir que hay que setear la clave que sube a ese nodo
    */
    private NodoMVias<K,V> dividirNodo(NodoMVias<K,V> nodoPadre,NodoMVias<K,V> nodoHijo){
        if(NodoMVias.esNodoVacio(nodoPadre)){//si el padre es nulo,solo entra aca cuando esta en la raiz inicial
            //creo los 2 nodos
            //si el hijo excede del numero maximo de claves entonces se debe dividir el nodo
            if(nodoHijo.nroDeClavesNoVacias()>this.nroMaxDeClaves){
                //creo los 2 nodos
                NodoMVias<K,V> supuestoNodoPadre=new NodoMVias<K,V>(this.orden+1,nodoHijo.getClave(this.nroMinDeClaves),nodoHijo.getValor(this.nroMinDeClaves));
                NodoMVias<K,V> supuestoHijoIzquierdo=this.crearNodoHijo(nodoHijo, 0);
                NodoMVias<K,V> supuestoHijoDerecho=this.crearNodoHijo(nodoHijo, this.nroMinDeClaves+1);
                supuestoNodoPadre.setHijo(0, supuestoHijoIzquierdo);
                supuestoNodoPadre.setHijo(1, supuestoHijoDerecho);
        
                return supuestoNodoPadre;
            }
            //si el hijo no excede en claves entonces solamente retorna el mismo
            return nodoHijo;
        }
        //si el nodoPadre no es nulo entonces ahi se deben subir las claves
        //aca entra cuando el nodo padre es no nulo,entonces las claves deben ser insertadas en ese padre
        if(nodoHijo.nroDeClavesNoVacias()>this.nroMaxDeClaves){
            int posicionAInsertar=super.posicionhijoparaSetear(nodoPadre, nodoHijo.getClave(this.nroMinDeClaves));
            //lo inserto
            super.insertarClaveEnNodo(nodoHijo.getClave(this.nroMinDeClaves), nodoHijo.getValor(this.nroMinDeClaves), nodoPadre);
            //creo los 2  nodos
            NodoMVias<K,V> supuestoHijoIzquierdo=this.crearNodoHijo(nodoHijo, 0);
            NodoMVias<K,V> supuestoHijoDerecho=this.crearNodoHijo(nodoHijo, this.nroMinDeClaves+1);
            //si no hay hijos mas adelante entonces inserta en la posicion y el otro 1+ de esa posicion
            if(!super.hayHijosMasAdelanteDeLaPosicion(nodoPadre, posicionAInsertar)){
                nodoPadre.setHijo(posicionAInsertar, supuestoHijoIzquierdo);
                nodoPadre.setHijo(posicionAInsertar+1, supuestoHijoDerecho);
            }else{//si hay hijos mas adelante se debe recorrer los hijos adelante
                
                for (int i = this.orden; i > posicionAInsertar+1; i--) {//en este for recorro los hijos
                    NodoMVias<K,V> p2=nodoPadre.getHijo(i-1);//prueba
                    nodoPadre.setHijo(i, nodoPadre.getHijo(i-1));
                     NodoMVias<K,V> p3=nodoPadre.getHijo(i);//prueba
            
                }
                //finalemente le seteo los hijos a sus respectivas posiciones,recordando que al dividir el nodo
                //de ser 1 se hace 2
                nodoPadre.setHijo(posicionAInsertar, supuestoHijoIzquierdo);
                nodoPadre.setHijo(posicionAInsertar+1, supuestoHijoDerecho);
                
            }
            
        }
        return nodoPadre;
    }
    /*
    Este metodo lo que hace es crear un nuevo nodo con sus hijos respectivos dado el nodo padre y la posicion inicio
    como en la reconstruccion de 0 hasta el punto donde esta la raiz en este caso de 0 a donde esta la clave a que sube
    y de la clave a subir +1 hasta el final,cabe recalcar que esto asume que el nodopadre esta con todas las claves llenas
    
    
    si M es 3
    nurmeroMaximodeHijos=M=3
    numeroMaximodeclaves=M-1=2
    numeroMinimoDeClaves=(M-1)/2=1(este es el que sube)
    numeroMinimoDeHijos=((M-1)/2)+1=2
    
    [10,20,30]
    en este caso sube el 20,entonces desde 0 hasta la posiciondelaclave que sube que es 1
    de 0 hasta ese limite se crea un nodo,y de esa posicionDelaclaveque sube+1 hasta el final
    se crea el otro nodo
    
    este metodo lo que hace es crear un nodo y cargarlo con los hijos que tenia 
    
    
    */
    private NodoMVias<K,V> crearNodoHijo(NodoMVias<K,V> nodoPadre,int posicionInicio){
        int limite=this.nroMinDeClaves;
        if(posicionInicio>this.nroMinDeClaves){
            limite=this.orden;
        }
        int i=posicionInicio;
        int posicionNodoHijo=0;
        NodoMVias<K,V> nuevoNodoHijo=new NodoMVias<K,V>(this.orden+1,nodoPadre.getClave(posicionInicio),nodoPadre.getValor(posicionInicio));
        //al ser un nuevo nodo sus hijos empezarian en 0 entonces por eso es que declaro contador llamdo posicionnuevohijo
        NodoMVias<K,V> prueba=nodoPadre.getHijo(i);//prueba
        nuevoNodoHijo.setHijo(posicionNodoHijo, nodoPadre.getHijo(i));//seteo el hijo inicial
        posicionNodoHijo++;
        //de cajon le seteo el primer hijo que tenga,a ese nuevo nodo,por eso el for comienza con uno mas
        for (i = posicionInicio+1; i < limite; i++) {
            prueba=nodoPadre.getHijo(i);//prueba
            nuevoNodoHijo.setClave(posicionNodoHijo, nodoPadre.getClave(i));
            nuevoNodoHijo.setValor(posicionNodoHijo, nodoPadre.getValor(i));
            //en este for mismo tambien le seteo los hijos que le corresponden a ese nuevo nodo
            nuevoNodoHijo.setHijo(posicionNodoHijo, nodoPadre.getHijo(i));
            posicionNodoHijo++;
        }
        prueba=nodoPadre.getHijo(i);//prueba
        nuevoNodoHijo.setHijo(posicionNodoHijo, nodoPadre.getHijo(i));//seteo el hijo restante
        return nuevoNodoHijo;
    }
    
    
    @Override
     public V eliminarClave( K clave) {
         V valorARetornar= this.buscarClave(clave);
         if(valorARetornar==null){
             return null;
         }
         this.raiz=this.eliminarClave(super.raiz,clave);
       
         //si la raiz no tiene ningun dato es decir,con tal de que su primera data sea nulo entonces quiere decir 
         //que debi agarrar el hijo en la posicion 0
         if(this.raiz.esDatoVacio(0)){
             this.raiz=this.raiz.getHijo(0);
         }
         return valorARetornar;
     }
     private NodoMVias<K,V> eliminarClave(NodoMVias<K,V> nodoActual,K claveAEliminar){
         
         for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if(nodoActual.getClave(i).compareTo(claveAEliminar)==0){//aca entra si lo encontre
                if(nodoActual.esHoja()){
                    //elimino la clave del nodo,y para no estar buscando en mi metodo anterior de eliminar
                    //hago esta version 2 donde ya le mando la posicion que debe eliminar
                    super.eliminarClaveDelNodov2(claveAEliminar, nodoActual,i);
                    //una vez eliminada la clave lo retorno el nodo actual
                    return nodoActual;
                }
                //si no es hoja
                //busca el antecesor inOrden o predecesor
                NodoMVias<K,V> AntecesorInOrden=super.obtenerAntecesorInOrden(nodoActual, i); 
                K claveAntecesorInOrden=AntecesorInOrden.getClave(0);
                V valorAntecesorInOrden=AntecesorInOrden.getValor(0);
                //luego por defecto ya se que el antecesor esta debajo del nodo actual,entonces
                //por eso le mando nodoActual.getHijo
                //ahora ese nodoActual puede caer en cualquiera de las situaciones ya mencionadas
                NodoMVias<K,V> supuestoHijo=this.eliminarClave(nodoActual.getHijo(i), claveAntecesorInOrden);
                //actualizo el nodoActual
                nodoActual.setClave(i,claveAntecesorInOrden);
                nodoActual.setValor(i, valorAntecesorInOrden);
                return this.prestarOfusionar(nodoActual, supuestoHijo, i);
            }
            if(claveAEliminar.compareTo(nodoActual.getClave(i))<0){//si la clave es menor que baje a ese nodo
                NodoMVias<K,V> supuestoHijo=this.eliminarClave(nodoActual.getHijo(i), claveAEliminar);
                return this.prestarOfusionar(nodoActual,supuestoHijo, i);
            }
         }
         //por si me falto revisar el ultimo hijo,capaz la clave a eliminar es mayor a todas las datas del nodo
         NodoMVias<K,V>supuestoHijo=this.eliminarClave(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),claveAEliminar);
         return this.prestarOfusionar(nodoActual, supuestoHijo,nodoActual.nroDeClavesNoVacias());
         
     }
     //la posicion donde bajar hace referencia a la posicion del nodoHijo
     //para no hacer una busqueda de nuevo 
     private NodoMVias<K,V> prestarOfusionar(NodoMVias<K,V> nodoPadre,NodoMVias<K,V> nodoHijo,int posicionDondeBaje){
        //si el nodo hijo cumple con la regla quiere decir que su numero de claves no vacias es mayor o igual al minimo de datos
         if(nodoHijo.nroDeClavesNoVacias()>=this.nroMinDeClaves){
             return nodoPadre;
         }
         //si no quiere decir que el hijo rompe la regla del minimo de claves
         //verificar si puedo prestarme del hermano en la siguiente posicion
         if(this.tengoHermanoMasAdelante(nodoPadre, posicionDondeBaje)){
             //aca entra cuando tiene hermano
             //del padre bajo a ese hijo hermano del hijo de la posicion donde baje
             NodoMVias<K,V> nodoHermano=nodoPadre.getHijo(posicionDondeBaje+1);//prueba
             if(this.puedoPrestarmeDeMiHermano(nodoHermano)){//si al prestarme no rompo la regla entonces entra
                 return this.prestarmeDelHermanoDeAdelante(nodoPadre, nodoHijo, posicionDondeBaje);
             }
             
         }
         //si nodf quiere decir que no puedo prestarme del hermano sgte o el hermano de adelante
             //entonces preguntare si puedo prestarme del hermano anterior
             //mas que pregunta es ver si es posible,para que sea posible tengo que ver que sus claves no sean menores
             //al minimo de claves
         if(this.tengoHermanoAtras(nodoPadre,posicionDondeBaje)){
             NodoMVias<K,V> hermanoDeAtras=nodoPadre.getHijo(posicionDondeBaje-1);
             if(this.puedoPrestarmeDeMiHermano(hermanoDeAtras)){
                 return this.prestarmeDelHermanoDeAtras(nodoPadre, nodoHijo, posicionDondeBaje);
             }
         }
         //si llega por aca es directo para hacer la fusion
         //si no tengo hermano atras directamente tengo que fusionarme
         if(this.tengoHermanoMasAdelante(nodoPadre, posicionDondeBaje)){
             NodoMVias<K,V> hermanoAdelante=nodoPadre.getHijo(posicionDondeBaje+1);
             //esta variable solo sirve para darle al eliminar una posicion correcta
             int posicionPadre=posicionDondeBaje=this.damePosicionPadre(nodoPadre, posicionDondeBaje);
             int cantidadDatosOcupados=nodoHijo.nroDeClavesNoVacias();//para saber cuantos datos ya tiene ese hijo donde me fusionare
            nodoHijo.setClave(cantidadDatosOcupados, nodoPadre.getClave(posicionDondeBaje));
            nodoHijo.setValor(cantidadDatosOcupados, nodoPadre.getValor(posicionDondeBaje));
             NodoMVias<K,V> nuevoNodoPadre=this.fusionarConElHermanoSiguiente(nodoPadre, hermanoAdelante,nodoHijo);
             super.eliminarClaveDelNodo(nuevoNodoPadre.getClave(posicionPadre), nuevoNodoPadre);
             this.eliminarHijo(nuevoNodoPadre,posicionDondeBaje+1);//por ultimo elimino ese hijo,que era del hermano
             return nuevoNodoPadre;
         }
         //si no tengo que fusionarme con el hermano de atras si o si
             int posicionHermanoAtras=posicionDondeBaje==0?0:posicionDondeBaje-1;
             NodoMVias<K,V> hermanoAtras=nodoPadre.getHijo(posicionHermanoAtras);
             //para saber cuantos datos ya tiene ese hijo donde me fusionare
             int cantidadDatosOcupados=hermanoAtras.nroDeClavesNoVacias();
             // esa funcion me dice la posicion del dato que fusionare del nodo padre
             int posicionDatoPadre=posicionDondeBaje==0?0:posicionDondeBaje-1;
             //luego seteo en la posicion correspondiente
             hermanoAtras.setClave(cantidadDatosOcupados, nodoPadre.getClave(posicionDatoPadre));
             hermanoAtras.setValor(cantidadDatosOcupados, nodoPadre.getValor(posicionDatoPadre));
             NodoMVias<K,V> nuevoNodoPadre=this.fusionarConElHermanoAnterior(nodoPadre, nodoHijo,hermanoAtras);
             super.eliminarClaveDelNodo(nuevoNodoPadre.getClave(posicionDatoPadre), nuevoNodoPadre);
             this.eliminarHijo(nuevoNodoPadre,posicionDondeBaje);//por ultimo elimino ese hijo donde baje
             return nuevoNodoPadre;
     }
     private int damePosicionPadre(NodoMVias<K,V>nodoPadre,int posicionDondeBaje){
             //si la posiciondonde baje es 0 retorno 0
             //si no pregunto si la posicion esta fuera de rango es decir si esta fuera de rango
             //si la posicion donde baje+1 es hijo vacio entonces estoy en la ultima clave
             //pero si accedo directamente a esa clave entonces me daria error de index fuera
             //entonces si pasa eso le quito menos 1 en caso de que no este fuera de rango simplemente
             //quiere decir que retorne la clave por que al acceder a esta no me dara error
             return posicionDondeBaje==0?0:nodoPadre.esHijoVacio(posicionDondeBaje+1)?posicionDondeBaje-1:posicionDondeBaje;
     }
     //metodo para prestarme del hermano de adelante
     //mandando como parametro el nodo padre,el nodo hijo(que es el nodo que puede incumplir la regla)
     //y por ultimo la posicion por donde baje
    private NodoMVias<K,V> prestarmeDelHermanoDeAdelante(NodoMVias<K,V> nodoPadre,NodoMVias<K,V> nodoHijo,int posicionDondeBaje){
        NodoMVias<K,V> nodoHermano=nodoPadre.getHijo(posicionDondeBaje+1);//agarro al hermano
        //con este metodo es para asegurarme que pueda obtener correctamente la clave y valor del nodo padre antes de cambiarlos
        int posicionClavePadre=nextAnterior(nodoPadre,posicionDondeBaje);
        K clavePadreAntesDeCambiar=nodoPadre.getClave(posicionClavePadre);
        V valorPadreAntesDeCambiar=nodoPadre.getValor(posicionClavePadre);
        //me presto la clave de mi hermano
        K clavePrestada=nodoHermano.getClave(0);
        V valorPrestado=nodoHermano.getValor(0);
        //elimino la clave del nodo hermano que me preste
        //en ese metodo las claves recorren
        super.eliminarClaveDelNodov2(clavePrestada, nodoHermano,0);//del hermano lo elimino su clave prestada
        //elimino su hijo asociado a esta clave para que los hijos recorran tambien
        NodoMVias<K,V> hijoEliminado=this.eliminarHijo(nodoHermano, 0);
        //luego al nodo padre le seteo en su posicionClave padre la clave prestada y valor prestado
        nodoPadre.setClave(posicionClavePadre, clavePrestada);
        nodoPadre.setValor(posicionClavePadre, valorPrestado);
        //el nodo hijo puede que tenga datos ya cargados entonces,agarro cuantos datos tiene
        //y en ese n lo inserto,no es necesario hacer n+1,dado que son claves
        int n=nodoHijo.nroDeClavesNoVacias();
        nodoHijo.setClave(n,clavePadreAntesDeCambiar);
        nodoHijo.setValor(n,valorPadreAntesDeCambiar);
        //luego de setear una clave y valor enttonces el nro de claves no vacias incrementa(n+1),en esa
        //posicion seteo el hijo que elimine
        nodoHijo.setHijo(n+1, hijoEliminado);
         return nodoPadre;//era nodo padre
    } 
    
    //metodo para prestarme del hermano de atras,donde le mando el nodo padre y el nodo hijo
    //ademas de la posicion donde baje
    private NodoMVias<K,V> prestarmeDelHermanoDeAtras(NodoMVias<K,V> nodoPadre,NodoMVias<K,V> nodoHijo,int posicionDondeBaje){
        NodoMVias<K,V> hermanoDeAtras=nodoPadre.getHijo(posicionDondeBaje-1);//obtengo al hermano de atras
        //con esta funcion me dice donde esta el dato del nodo padre
        int posicionDatoPadre=obtenerPosicionClavePadreParaPrestarDeAtras(posicionDondeBaje);
        //obtengo las clave valor del nodopadre antes de cambiarlo
        K clavePadreAntesDeCambiar=nodoPadre.getClave(posicionDatoPadre);
        V valorPadreAntesDeCambiar=nodoPadre.getValor(posicionDatoPadre);
        //n para agarrar el ultimo del hermano de atras
        int n=hermanoDeAtras.nroDeClavesNoVacias();
        //obtengo la clave valor del hermano donde me prestare
        //aqui n-1 por que si tiene 3 claves las claves,van de 0..2
        K clavePrestada=hermanoDeAtras.getClave(n-1);
        V valorPrestado=hermanoDeAtras.getValor(n-1);
        //elimino la clave del nodov2,mandando una posicion para que a partir de esa posicion lo elimine
        super.eliminarClaveDelNodov2(clavePrestada, hermanoDeAtras,n-1);//de ese hermano lo elimino su clave del nodo
        NodoMVias<K,V> hijoEliminado=this.eliminarHijo(hermanoDeAtras, n);
        //ahora debo setear esa clave prestada y valor prestado al nodo hijo
        //lo debo insertar ordenadamente dado que suponiendo que el nodo hijo tenga datos
        //al pasarle el clave valor del padre tiene que insertarse en la primera posicion
        //haciendo que los demas recorran,igualmente los hijos
        //ese metodo recorroClaves e hijos
        this.insertarHijoYDatoEnlaPrimeraPosicion(nodoHijo, hijoEliminado, clavePadreAntesDeCambiar, valorPadreAntesDeCambiar);
        nodoPadre.setClave(posicionDatoPadre, clavePrestada);
        nodoPadre.setValor(posicionDatoPadre, valorPrestado);
        return nodoPadre;//era nodo padre
    }
    //este metodo reemplaza al nextAnterior
    
    private static int obtenerPosicionClavePadreParaPrestarDeAtras(int posicion){
        return posicion==0?0:posicion-1;
    }
    //funcion que me retorna de donde debo sacar la clave del nodopadre
    //y para explicarlo si la posicino que le mando+1 es hijo vacio quiere decir que el dato debo sacarlo de esa posicion-1
    //si por ejemplo tengo este arbol
    /*
            [98  160  500]
    [80,90]   [100,140]   [200,300]   [600]
    
    si quisiera eliminar el 600 
    el 600 al eliminarlo quedaria vacio entonces quiere decir que baje por el hijo 3
    pero,no existe dato 3,a lo mucho hay dato 2
    entonces si la posicion por donde baje+1 es hijo vacio,quiere decir que estoy en la ultima clave basicamente
    entonces si es asi para obtener la ultima clave,debo restar-1,en cualquier otro caso simplemente retorno la posicion
    por que son posiciones validas para agarra el dato del nodo padre
    */
    private  int nextAnterior(NodoMVias<K,V> nodoPadre,int posicionPorDondeBaje){
       return nodoPadre.esHijoVacio(posicionPorDondeBaje+1)?posicionPorDondeBaje-1:posicionPorDondeBaje;
    }
    
    
   //este metodo inserta la clave en la posicion 0 de un nodo,a su vez,tambien setea el hijo en la posicion 0
    //y recorre los demas hijos a la derecha
    private void insertarHijoYDatoEnlaPrimeraPosicion(NodoMVias<K,V> nodoDondeInsertare,NodoMVias<K,V> hijoAInsertar,K claveAInsertar,V valorAInsertar){
       int n=nodoDondeInsertare.nroDeClavesNoVacias();
        for (int i = n; i > 0; i--) {
            nodoDondeInsertare.setClave(i, nodoDondeInsertare.getClave(i-1));
            nodoDondeInsertare.setValor(i, nodoDondeInsertare.getValor(i-1));
            nodoDondeInsertare.setHijo(i+1, nodoDondeInsertare.getHijo(i));
        }
        //en el for hago 2 trabajos,recorrer los hijos y las clave valor,pero dada la condicion
        //me faltaria procesar un hijo y eso lo hago manual en la sgte linea
        nodoDondeInsertare.setHijo(1,nodoDondeInsertare.getHijo(0));
        //finalmente inserto el hijo y la clave valor en la posicion 0
        nodoDondeInsertare.setHijo(0, hijoAInsertar);
        nodoDondeInsertare.setValor(0,valorAInsertar);
        nodoDondeInsertare.setClave(0, claveAInsertar);
        
    }
     //metodo para contar los hijos de un nodo,pero teniendo en cuenta que un arbol b generalmente
     //sus hijos estan pegados uno tras de otro,no como el mvias
     private int cantidadHijosActualesDelNodo(NodoMVias<K,V> nodoActual){
         int c=0;
         for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
             if(!nodoActual.esHijoVacio(i)){
                 c++;
             }
         }
         if(!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())){
             c++;
         }
         return c;
     }
     //cuando me fusiono,me debo fusionar en el nodoHijo,dado que ese hijo es el nodoProblema
     //se fusiona del nodo padre en la posicion por donde bajo
        
     
     private static int obtenerPosicionDondeFusionar(int posicionHijoProblema,int posicionHermano){
         if(posicionHermano<posicionHijoProblema){//en el caso de que la posicion del hermano sea menor a la posicion por donde baje
             //entonces debo resetear la posicion por donde baje,esto lo hago,para cuando toque fusionar
             //la fusion se haga en el hermano y no en el nodo problema
             //creo que solamente pasa esto cuando el hermano esta antes del nodo problema
             return posicionHermano;
         }
         //si no
         return posicionHijoProblema;
     }
     //asumo que si entra a fusionar con el hermano anterior quiere decir que no se puede fusionar con el hermano anterior
     //cuando me fusiono con el hermano de atras quiere decir que me tengo que fusionar en ese hermano 
     //donde debo mover la clave del nodo padre y pasar todos los datos del nodo hijo que ocaciona el problema
     private NodoMVias<K,V> fusionarConElHermanoAnterior(NodoMVias<K,V> nodoPadre,NodoMVias<K,V> nodoHijoProblema,NodoMVias<K,V> nodoDondeFusionare){
         //NodoMVias<K,V> nodoDondeFusionare=nodoHermano;
         
         int cantidadDatosOcupados=nodoDondeFusionare.nroDeClavesNoVacias();
         
         int n=nodoHijoProblema.nroDeClavesNoVacias();
         int i=0;
         for (i = 0; i < n; i++) {
             nodoDondeFusionare.setClave(cantidadDatosOcupados, nodoHijoProblema.getClave(i));
             nodoDondeFusionare.setValor(cantidadDatosOcupados, nodoHijoProblema.getValor(i));
             nodoDondeFusionare.setHijo(cantidadDatosOcupados, nodoHijoProblema.getHijo(i));
             cantidadDatosOcupados++;
         }
         //puede que aca este un problema con el i
        nodoDondeFusionare.setHijo(cantidadDatosOcupados, nodoHijoProblema.getHijo(i));//faltaria eso que (i) simula el nro de claves no vacias
         return nodoPadre;//era nodo padre
         
     }
     private NodoMVias<K,V> fusionarConElHermanoSiguiente(NodoMVias<K,V> nodoPadre,NodoMVias<K,V> nodoHermano,NodoMVias<K,V> nodoDondeFusionare){
     //    NodoMVias<K,V>nodoDondeFusionare=nodoPadre.getHijo(posicionHijoProblema);//aca es donde fusionare a los 2 nodos,por que este deberia estar vacio
         int i=0;
         int cantidadDatosOcupados=nodoDondeFusionare.nroDeClavesNoVacias();
         for (i = 0; i < nodoHermano.nroDeClavesNoVacias(); i++) {
             nodoDondeFusionare.setValor(cantidadDatosOcupados,nodoHermano.getValor(i));
             nodoDondeFusionare.setClave(cantidadDatosOcupados,nodoHermano.getClave(i));
             nodoDondeFusionare.setHijo(cantidadDatosOcupados, nodoHermano.getHijo(i));
             //antes era i+1
             cantidadDatosOcupados++;
         }
         //antes era i+1 en ves de cantidadDatosoucopados
         nodoDondeFusionare.setHijo(cantidadDatosOcupados, nodoHermano.getHijo(i));//faltaria eso que (i) simula el nro de claves no vacias
         return nodoPadre;//era nodo padre
     }
             
     
     private boolean tengoHermanoAtras(NodoMVias<K,V> nodoReferencia,int posicionReferencia){
         return (posicionReferencia-1)==-1?false:!NodoMVias.esNodoVacio(nodoReferencia.getHijo(posicionReferencia-1));
     }
     
     //verificar del ultimo hijo,hay puede dar error
     //metodo para verificar si hay hermano en la siguiente posicion donde estoy parado
     private boolean tengoHermanoMasAdelante(NodoMVias<K,V> nodoReferencia,int posicionReferencia){
         if(posicionReferencia+1>this.orden){
             return false;
         }
         return !NodoMVias.esNodoVacio(nodoReferencia.getHijo(posicionReferencia+1));
     }
     //este metodo me devolvera true si puedo prestarme de mi hermano,devuelve true si puedo prestarme
     //cuando puedo prestarme de mi hermano,quiere decir que al prestarme no inflinje la regla
     private boolean puedoPrestarmeDeMiHermano(NodoMVias<K,V> nodoHermano){
         return (nodoHermano.nroDeClavesNoVacias()-1)>=this.nroMinDeClaves;
     }
     //metodo para eliminar un hijo del nodo
     //donde se elimina ese hijo,los demas recorren
     //ademas que me retorna el hijo que elimine
     private NodoMVias<K,V> eliminarHijo(NodoMVias<K,V> nodoReferencia,int numeroDeHijo){
         NodoMVias<K,V> hijoEliminado=nodoReferencia.getHijo(numeroDeHijo);
         for (int i = numeroDeHijo+1; i <= this.orden; i++) {
             nodoReferencia.setHijo(i-1, nodoReferencia.getHijo(i));
         }
         return hijoEliminado;
     }
     
            
    @Override
    public List<K> recorridoEnPreOrden() {
        return super.recorridoEnPreOrden();
    }

    @Override
    public List<K> recorridoEnInOrden() {
        return super.recorridoEnInOrden();
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        return super.recorridoEnPostOrden();
    }

    @Override 
    public List<K> recorridoEnPorNiveles(){
        return super.recorridoEnPorNiveles();
    }
    //para lista de valores
    @Override
    public List<V> recorridoenPreOrden() {
        return super.recorridoenPreOrden();
    }

    @Override
    public List<V> recorridoenInOrden() {
        return super.recorridoenInOrden();
    }

    @Override
    public List<V> recorridoenPostOrden() {
        return super.recorridoenPostOrden();
    }

    @Override
    public List<V> recorridoenPorNiveles() {
        return super.recorridoenPorNiveles();
    }
    
    @Override
    public int cantidadClaves(){
        return super.cantidadClaves();
    }
    @Override
             
    public int size(){
        return super.size();
    }//contar los nodos
    @Override
    public int altura(){
        return super.altura();
    }//altura del arbol
    @Override
    public void vaciar(){
        super.vaciar();
    }
    @Override
    public boolean esArbolVacio(){
        return super.esArbolVacio();
    }
    @Override
    public int nivel(){
        return super.nivel();
    }
}
