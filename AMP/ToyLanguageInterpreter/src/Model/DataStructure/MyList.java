package Model.DataStructure;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;

public class MyList<T> implements IList<T>{
    private List<T> list;

    // default ctor
    public MyList() {
        this.list = new ArrayList<>();
    }
    public MyList(List<T> list) {
        this.list = list;
    }

    @Override
    public void add(T to_add) {
        this.list.add(to_add);
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();

        for (T i : list)
            print.append(i.toString()).append(", ");

        print.setLength(max(print.length() - 2, 0));
        return print.toString();
    }
}
