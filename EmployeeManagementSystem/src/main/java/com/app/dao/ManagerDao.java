package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Manager;

@Repository
public interface ManagerDao extends JpaRepository<Manager, Integer> {

}
