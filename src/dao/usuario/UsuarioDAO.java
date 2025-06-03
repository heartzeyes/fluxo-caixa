package dao.usuario;

import model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    List<Usuario> getAll();
    Usuario getById(int id);
    void insert(Usuario usuario);
    void update(Usuario usuario);
    void deleteById(int id);
}
