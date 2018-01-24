package Model.DataStructure;

import Model.DataStructure.IStack;
import Model.DataStructure.MyStack;
import Model.Exceptions.DataStructureEmpty;

public class HeapAddressBuilder {
    private Integer current_address = 1;
    private static IStack<Integer> free_address = new MyStack<>();

    public Integer getFreeAddress() throws DataStructureEmpty {
        return free_address.empty() ? this.current_address++ : free_address.pop();
    }
}
