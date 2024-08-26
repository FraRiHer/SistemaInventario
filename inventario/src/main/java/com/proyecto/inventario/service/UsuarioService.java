package com.proyecto.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.inventario.entities.Usuario;
import com.proyecto.inventario.repository.UsuarioRepository;




@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }


    public Usuario save(Usuario u){
        return usuarioRepository.save(u);
    }

    public Usuario getUsuarioById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

}
