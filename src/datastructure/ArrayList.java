package datastructure;

public class ArrayList implements List {
    private static final int DEFAULT_CAPACITY = 128;

    private String[] elements;
    private int size;

    public ArrayList() {
        this.elements = new String[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public String select(int index) {
        validateElementIndex(index);
        return elements[index];
    }

    @Override
    public void insert(int index, String data) {
        validatePositionIndex(index);
        ensureCapacity(size + 1);

        if (index < size) {
            System.arraycopy(elements, index, elements, index + 1, size - index);
        }

        elements[index] = data;
        size++;
    }

    @Override
    public void delete(int index) {
        validateElementIndex(index);

        int movedCount = size - index - 1;
        if (movedCount > 0) {
            System.arraycopy(elements, index + 1, elements, index, movedCount);
        }

        elements[--size] = null;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity <= elements.length) {
            return;
        }

        int newCapacity = elements.length * 2;
        while (newCapacity < requiredCapacity) {
            newCapacity *= 2;
        }

        String[] expanded = new String[newCapacity];
        System.arraycopy(elements, 0, expanded, 0, size);
        elements = expanded;
    }

    private void validateElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }

    private void validatePositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }
}
