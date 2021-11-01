/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.dao;

import java.sql.SQLException;

/**
 *
 * @author ozluuh
 */
public interface CrudDao<E, K> extends BaseDao<E, K> {

	void store(E entity) throws SQLException;

	int update(E entity) throws SQLException;

	int destroy(K id) throws SQLException;
}
