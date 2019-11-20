package ajeffrey.teaching.util.stack;
import net.jcip.annotations.GuardedBy;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SafeStackImpl extends SafeStack implements UnsafeStack {

    @GuardedBy("this")
    static final UnsafeStack contents = UnsafeStack.factory.build ();

    protected int version = 0;

    protected int size = 0;

    public SafeStackImpl (){
    }

    @Override
    public synchronized void push(Object element) {
        synchronized (this){
            contents.push(element);
            size();
            ++version;
        }
    }

    @Override
    public synchronized Object pop() {
        synchronized (this){
            ++version;
            return contents.pop();
        }
    }

    @Override
    public synchronized int size() {
        return this.size = contents.size();
    }

    @Override
    public synchronized Iterator iterator() {
        synchronized (this){
            return new SafeStackIterator(contents, size);
        }
    }

    protected class SafeStackIterator implements Iterator {
        protected UnsafeStack contents;
        protected int currentVersion;
        public int size;
        protected int index = 0;

        SafeStackIterator(final UnsafeStack contents, final int size) {
            synchronized (this){
                this.currentVersion = version;
                this.size = size;
                this.contents = contents;
            }
        }

        public synchronized boolean hasNext () {
            synchronized (SafeStackImpl.this){
                return (index < size);
            }
        }

        public synchronized Object next () {
            Object result = null;

            synchronized (SafeStackImpl.this) {
                if (currentVersion != version) {
                    throw new ConcurrentModificationException();
                } else {
                    result = pop();
                    size = contents.size();
                    currentVersion = version;
                }
            }
            return result;
        }

        public synchronized void remove () {
            synchronized (SafeStackImpl.this) {
                throw new UnsupportedOperationException();
            }
        }
    }
}
