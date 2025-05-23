package interfaces;

import java.util.List;

public interface Cadastro<T> {
    void adicionar(T entidade);
    void editar(T entidade);
    void remover(int id);
    T buscarPorId(int id);
    List<T> listarTodos();
}