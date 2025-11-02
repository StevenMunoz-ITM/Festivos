package festivos.api.dominio.dtos;

public class ValidacionFestivoDto {
    
    private boolean esFestivo;
    private String mensaje;
    private String nombreFestivo;
    private String tipoFestivo;
    
    public ValidacionFestivoDto() {
    }
    
    public ValidacionFestivoDto(boolean esFestivo, String mensaje) {
        this.esFestivo = esFestivo;
        this.mensaje = mensaje;
    }
    
    public ValidacionFestivoDto(boolean esFestivo, String mensaje, String nombreFestivo, String tipoFestivo) {
        this.esFestivo = esFestivo;
        this.mensaje = mensaje;
        this.nombreFestivo = nombreFestivo;
        this.tipoFestivo = tipoFestivo;
    }
    
    public boolean isEsFestivo() {
        return esFestivo;
    }
    
    public void setEsFestivo(boolean esFestivo) {
        this.esFestivo = esFestivo;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getNombreFestivo() {
        return nombreFestivo;
    }
    
    public void setNombreFestivo(String nombreFestivo) {
        this.nombreFestivo = nombreFestivo;
    }
    
    public String getTipoFestivo() {
        return tipoFestivo;
    }
    
    public void setTipoFestivo(String tipoFestivo) {
        this.tipoFestivo = tipoFestivo;
    }
    
    @Override
    public String toString() {
        return "ValidacionFestivoDto{" +
                "esFestivo=" + esFestivo +
                ", mensaje='" + mensaje + '\'' +
                ", nombreFestivo='" + nombreFestivo + '\'' +
                ", tipoFestivo='" + tipoFestivo + '\'' +
                '}';
    }
}