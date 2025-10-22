package festivos.api.aplicacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseService<T, DTO, ID> {

    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract Object getMapper();
    protected abstract DTO mapToDTO(T entity);
    protected abstract T mapToEntity(DTO dto);
    protected abstract List<DTO> mapToDTOList(List<T> entities);
    protected abstract void updateEntityFromDTO(T entity, DTO dto);
    protected abstract String getEntityName();

    @Transactional(readOnly = true)
    public List<DTO> obtenerTodos() {
        return mapToDTOList(getRepository().findAll());
    }

    @Transactional(readOnly = true)
    public Optional<DTO> obtenerPorId(ID id) {
        return getRepository().findById(id)
                .map(this::mapToDTO);
    }

    public DTO guardar(DTO dto) {
        T entity = mapToEntity(dto);
        T savedEntity = getRepository().save(entity);
        return mapToDTO(savedEntity);
    }

    public DTO actualizar(ID id, DTO dto) {
        T existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException(getEntityName() + " no encontrado con ID: " + id));
        
        updateEntityFromDTO(existingEntity, dto);
        T updatedEntity = getRepository().save(existingEntity);
        return mapToDTO(updatedEntity);
    }

    public void eliminar(ID id) {
        if (!getRepository().existsById(id)) {
            throw new IllegalArgumentException(getEntityName() + " no encontrado con ID: " + id);
        }
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existe(ID id) {
        return getRepository().existsById(id);
    }
}