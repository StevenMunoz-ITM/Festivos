package festivos.api.dominio.dtos;

public class FestivoEntradaDto {
    
    private int id;
    private String nombre;
    private int dia;
    private int mes;
    private int diasPascua;
    private int paisId;
    private int tipoFestivoId;
    
    public FestivoEntradaDto() {
    }
    
    public FestivoEntradaDto(int id, String nombre, int dia, int mes, int diasPascua, 
                            int paisId, int tipoFestivoId) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.paisId = paisId;
        this.tipoFestivoId = tipoFestivoId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getDia() {
        return dia;
    }
    
    public void setDia(int dia) {
        this.dia = dia;
    }
    
    public int getMes() {
        return mes;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public int getDiasPascua() {
        return diasPascua;
    }
    
    public void setDiasPascua(int diasPascua) {
        this.diasPascua = diasPascua;
    }
    
    public int getPaisId() {
        return paisId;
    }
    
    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }
    
    public int getTipoFestivoId() {
        return tipoFestivoId;
    }
    
    public void setTipoFestivoId(int tipoFestivoId) {
        this.tipoFestivoId = tipoFestivoId;
    }
    
    @Override
    public String toString() {
        return "FestivoEntradaDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dia=" + dia +
                ", mes=" + mes +
                ", diasPascua=" + diasPascua +
                ", paisId=" + paisId +
                ", tipoFestivoId=" + tipoFestivoId +
                '}';
    }
}
