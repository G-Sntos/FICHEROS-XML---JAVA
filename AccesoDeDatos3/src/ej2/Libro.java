package ej2;

public class Libro {
	private String Titulo;
	private String Autor;
	private String Editorial;
	private String ISBN;
	private int ano;
	private int paginas;
	
	public Libro(String titulo, String autor, String editorial, String isbn, int ano, int paginas) {
		super();
		this.Titulo = titulo;
		this.Autor = autor;
		this.Editorial = editorial;
		this.ISBN = isbn;
		this.ano = ano;
		this.paginas = paginas;
	}
	public String getTitulo() {
		return Titulo;
	}
	public void setTitulo(String titulo) {
		this.Titulo = titulo;
	}
	public String getAutor() {
		return Autor;
	}
	public void setAutor(String autor) {
		this.Autor = autor;
	}
	public String getEditorial() {
		return Editorial;
	}
	public void setEditorial(String editorial) {
		this.Editorial = editorial;
	}
	public String getIsbn() {
		return ISBN;
	}
	public void setIsbn(String isbn) {
		this.ISBN = isbn;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	
}
