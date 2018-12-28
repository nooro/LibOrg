
public class Book {
	
	private int id;
	private String title;
	private String author;
	private String genre;
	private int publishingYear;
	private int pages;
	private int amount;
	
	
	//Constructors
	public Book(int id, String title, String author, String genre, int year, int pages, int amount ) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.genre = genre;
		this.publishingYear = year;
		this.pages = pages;
		this.amount = amount;
	}
	
	public Book() {
		this.author = "<unknown>";
		this.title = "<unknown>";
		this.genre = "<unknown>";
		this.publishingYear = 0;
		this.pages = 0;
		this.amount = 0;
		this.id = 0;
	}

	//Getters
	public String getAutor() { return this.author; }
	public String getTitle() { return this.title; }
	public String getGenre() { return this.genre; }
	public int getYear() { return this.publishingYear; }
	public int getPages() { return this.pages; }
	public int getAmount() { return this.amount; }
	public int getID() { return this.id; }
	
	//Setters
	public void setAutor( String autor ) { this.author = autor; }
	public void setTitle( String title ) { this.title = title; }
	public void setGenre( String genre ) { this.genre = genre; }
	public void setPublishingYear( int year ) { this.publishingYear = year; }
	public void setPages( int pages ) { this.pages = pages; }
	public void setAmount( int amount ) { this.amount = amount; }
	public void setID( int id ) { this.id = id; }
	
}
