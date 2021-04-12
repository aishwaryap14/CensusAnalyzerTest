package com.project;

import java.io.Reader;
import java.util.Iterator;

public interface ICsvBuilder<E> {
    public  Iterator<E> loadIndiaCSVFile(Reader reader, Class csvClass);
}
