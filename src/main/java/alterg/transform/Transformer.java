package alterg.transform;

public interface Transformer<S, D> {

    D transform(S source);

}
