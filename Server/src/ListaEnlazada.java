/**
 * La implementación de la clase Nodo
 * @author Adrian Sáchez
 * 
 *
 */

class Nodo{
		Object dato;
		Nodo siguiente;
		
		public Nodo(Object elem){
			dato=elem;
			siguiente=null;
		}
	}
/**
 * La implementación de la clase ListaEnlazada
 * @author Adrian Sáchez
 * 
 */
public class ListaEnlazada {
	private Nodo cabeza;
	private int numElementos;
	/**
	 * 
	 */
	public ListaEnlazada(){
		cabeza=null;
		numElementos=0;
	}
	/**
	 * 
	 * @param elem
	 */
	public void add(Object elem){
		if (numElementos==0){
			cabeza = new Nodo(elem);
		}
		else{
			obtenerNodo(numElementos-1).siguiente = new Nodo(elem);
		}
		numElementos++;
	}
	/**
	 * Obtiene a un nodo con un indice especifico
	 * @param indice
	 * @return actual
	 */
	private Nodo obtenerNodo(int indice){
		if(indice>= numElementos || indice <0){
			throw new IndexOutOfBoundsException("Indice incorrecto:"+indice);
		}
		
		Nodo actual=cabeza;
		for (int i=0; i<indice; i++)
			actual=actual.siguiente;
		return actual;
	}
	/**
	 * Busca el indice de un elemento especifico
	 * @param elem
	 * @return
	 */
	public int indexOf(Object elem){
		int indice;
		boolean encuentra=false;
		Nodo actual=cabeza;
		for(indice=0; actual!=null; indice++, actual=actual.siguiente){
			if((actual.dato!=null && actual.dato.equals(elem))||((actual.dato==null)&&(elem==null))){
				encuentra=true;
				break;
			}
		}
		if(encuentra==false)
			indice=-1;
		return indice;
	}
	/**
	 * Remueve un elemento de la lista
	 * @param indice
	 * @return el dato a eliminar
	 */
	public Object remove(int indice){
		Nodo actual=null;
		Nodo anterior=null;
		if(indice>0){
			anterior =obtenerNodo(indice-1);
			actual=anterior.siguiente;
			anterior.siguiente=actual.siguiente;
			numElementos--;
		}
		if(indice==0){
			actual=cabeza;
			cabeza=cabeza.siguiente;
			numElementos--;
		}
		if(actual!=null)
			return actual.dato;
		else
			return null;
	}
	/**
	 * Reomover elemento
	 * @param elem
	 * @return
	 */
	public int remove(Object elem){
		int actual=indexOf(elem);
		if(actual!=-1)
			remove(actual);
		
		return actual;
	}
	/**
	 * Obtiene el valor de un elemento de la lista
	 * @param indice
	 * @return
	 */
	public Object get(int indice){
		return obtenerNodo(indice).dato;
	}
	/**
	 * Tamaño de la lista
	 * @return size
	 */
	public int size(){
		return numElementos;
	}
	
}

