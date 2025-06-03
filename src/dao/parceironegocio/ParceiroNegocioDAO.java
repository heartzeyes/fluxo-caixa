package dao.parceironegocio;

import model.ParceiroNegocio;
import java.util.List;

public interface ParceiroNegocioDAO {
    List<ParceiroNegocio> getAll();
    ParceiroNegocio getById(int id);
    void insert(ParceiroNegocio parceiroNegocio);
    void update(ParceiroNegocio parceiroNegocio);
    void deleteById(int id);
}
