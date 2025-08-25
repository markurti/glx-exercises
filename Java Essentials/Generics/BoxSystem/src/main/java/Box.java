public class Box<T> {
    private T surprise;

    public Box(T surprise) {
        this.surprise = surprise;
    }

    public <E> boolean inspect(E objectToCompare) {
        return surprise.getClass().equals(objectToCompare.getClass());
    }
}
