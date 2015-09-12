

class Nodo{
		Object dato;
		Nodo siguiente;
		
		public Nodo(Object elem){
			dato=elem;
			siguiente=null;
		}
	}
public class ListaEnlazada {
	private Nodo cabeza;
	private int numElementos;
	
	public ListaEnlazada(){
		cabeza=null;
		numElementos=0;
	}
	public void add(Object elem){
		if (numElementos==0){
			cabeza = new Nodo(elem);
		}
		else{
			obtenerNodo(numElementos-1).siguiente = new Nodo(elem);
		}
		numElementos++;
	}
	private Nodo obtenerNodo(int indice){
		if(indice>= numElementos || indice <0){
			throw new IndexOutOfBoundsException("Indice incorrecto:"+indice);
		}
		
		Nodo actual=cabeza;
		for (int i=0; i<indice; i++)
			actual=actual.siguiente;
		return actual;
	}
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
	public Object get(int indice){
		return obtenerNodo(indice).dato;
	}
	public int size(){
		return numElementos;
	}
	
}
