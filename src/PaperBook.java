public class PaperBook extends Book {
    private int pageCount;
    private String location;

    public PaperBook(){
        super();
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public PaperBook(int id, String bookName, String author, String publisher, double price, boolean isBorrowed, int pageCount, String location){
        super(id, bookName, author, publisher, price, isBorrowed);
        this.pageCount = pageCount;
        this.location = location;
    }

    @Override
    public String getType(){
        return "PaperBook";
    }

    @Override
    public String getExtraInfo(){
        return "  页数："+pageCount+"\n"+"  位置："+location;
    }

    @Override
    public String getExtraData(){
        return pageCount+"|"+location;
    }
}
