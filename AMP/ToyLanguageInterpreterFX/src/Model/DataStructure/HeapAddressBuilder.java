package Model.DataStructure;

import Model.Exceptions.DataStructureEmpty;

public class HeapAddressBuilder {
    private static IStack<Integer> free_address = new MyStack<>();
    private Integer current_address = 1;

    public Integer getFreeAddress() throws DataStructureEmpty {
        return free_address.empty() ? this.current_address++ : free_address.pop();
    }
}
