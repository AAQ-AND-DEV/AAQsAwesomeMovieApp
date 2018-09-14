package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import com.aaqanddev.aaqsawesomeandroidapp.RepositoryException;

public interface IAaqMovieRepository<T extends BaseModel> {

    T saveItem(T record) throws RepositoryException;

}
