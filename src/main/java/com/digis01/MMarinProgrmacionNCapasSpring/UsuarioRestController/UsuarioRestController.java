package com.digis01.MMarinProgrmacionNCapasSpring.UsuarioRestController;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.RolDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.UsuarioDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.ErrorFile;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.ResultFile;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.UsuarioDireccion;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarioapi")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @GetMapping()
    public ResponseEntity GetAll() {
        Result result = this.usuarioDAOImplementation.GetAll();
        if (result.correct) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/UsuarioDireccion/{IdUsuario}")
    public ResponseEntity DireccionesByIdUsuarioJPA(@PathVariable int IdUsuario) {
        Result result = this.usuarioDAOImplementation.DireccionesByIdUsuarioJPA(IdUsuario);
        if (result.correct) {
            if (result.object != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/UsuarioById/{IdUsuario}")
    public ResponseEntity UsuarioById(@PathVariable int IdUsuario) {
        Result result = this.usuarioDAOImplementation.UsuarioByIdJPA(IdUsuario);
        if (result.correct) {
            if (result.object != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(null);
            }

        } else {
            return ResponseEntity.status(400).body(null);
        }

    }

    @PostMapping("/Add")
    public ResponseEntity Add(@Valid @RequestBody UsuarioDireccion usuarioDireccion, BindingResult BindingResult) {
        try {
            Result result = new Result();
            if (BindingResult.hasErrors()) {
                result.correct = false;
                result.object = BindingResult.getFieldError("Usuario.Nombre");
                return ResponseEntity.status(400).body(result);
            } else {
                result = this.usuarioDAOImplementation.Add(usuarioDireccion);
                if (result.correct) {
                    return ResponseEntity.status(201).body(result);
                } else {
                    return ResponseEntity.status(400).body(null);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("Update/{IdUsuario}")
    public ResponseEntity UsuarioUpdate(@PathVariable int IdUsuario, @RequestBody Usuario usuario) {
        Result result = this.usuarioDAOImplementation.UsuarioUpdateJPA(IdUsuario, usuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }

    }

    @DeleteMapping("/Delete/{IdUsuario}")
    public ResponseEntity UsuarioDelete(@PathVariable int IdUsuario) {
        Result result = this.usuarioDAOImplementation.UsuarioDireccionDeleteJPA(IdUsuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PatchMapping("/UsuarioUpdateByEstatus/{IdUsuario}")
    public ResponseEntity UsuarioUpdateByEstatus(@PathVariable int IdUsuario, @RequestBody Usuario usuario) {
        try {
            Result result = this.usuarioDAOImplementation.UsuarioUpdateByEstatusJPA(IdUsuario, usuario.getEstatus());
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(400).body(null);
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/busquedaDinamica")
    public ResponseEntity BusquedaDinamica(@RequestBody Usuario usuario) {
        try {
            Result result = new Result();
            result.correct = true;
            List<UsuarioDireccion> usuariosGeneral = this.usuarioDAOImplementation.GetAll().objects.stream().map(u -> (UsuarioDireccion) u).collect(Collectors.toList());
            if (usuario.Rol.getIdRol() == 0 && usuario.getEstatus() == -1) {
                result.objects = usuariosGeneral.stream()
                        .filter(u -> (u.Usuario.getNombre().toLowerCase().contains(usuario.getNombre().toLowerCase()))
                        && (u.Usuario.getApellidoPaterno().toLowerCase().contains(usuario.getApellidoPaterno().toLowerCase()))
                        && (u.Usuario.getApellidoMaterno().toLowerCase().contains(usuario.getApellidoMaterno().toLowerCase())))
                        .collect(Collectors.toList());
            } else {
                if (usuario.Rol.getIdRol() > 0 && usuario.getEstatus() == -1) {
                    result.objects = usuariosGeneral.stream().filter(u -> (u.Usuario.getNombre().toLowerCase().contains(usuario.getNombre().toLowerCase()))
                            && (u.Usuario.getApellidoPaterno().toLowerCase().contains(usuario.getApellidoPaterno().toLowerCase()))
                            && (u.Usuario.getApellidoMaterno().toLowerCase().contains(usuario.getApellidoMaterno().toLowerCase()))
                            && (u.Usuario.Rol.getIdRol() == usuario.Rol.getIdRol()))
                            .collect(Collectors.toList());

                } else if (usuario.Rol.getIdRol() == 0) {
                    result.objects = usuariosGeneral.stream().filter(u -> (u.Usuario.getNombre().toLowerCase().contains(usuario.getNombre().toLowerCase()))
                            && (u.Usuario.getApellidoPaterno().toLowerCase().contains(usuario.getApellidoPaterno().toLowerCase()))
                            && (u.Usuario.getApellidoMaterno().toLowerCase().contains(usuario.getApellidoMaterno().toLowerCase()))
                            && (u.Usuario.getEstatus() == usuario.getEstatus()))
                            .collect(Collectors.toList());

                } else {
                    result.objects = usuariosGeneral.stream().filter(u -> (u.Usuario.getNombre().toLowerCase().contains(usuario.getNombre().toLowerCase()))
                            && (u.Usuario.getApellidoPaterno().toLowerCase().contains(usuario.getApellidoPaterno().toLowerCase()))
                            && (u.Usuario.getApellidoMaterno().toLowerCase().contains(usuario.getApellidoMaterno().toLowerCase()))
                            && (u.Usuario.getEstatus() == usuario.getEstatus())
                            && (u.Usuario.Rol.getIdRol() == usuario.Rol.getIdRol()))
                            .collect(Collectors.toList());
                }

            }
            if (result.correct) {
                if (result.objects.isEmpty()) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.ok(result);
                }
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/CargaMasiva")
    public ResponseEntity CargaMasiva(@RequestParam MultipartFile archivo) {
        Result result = new Result();
        try {
            if (!archivo.isEmpty() && archivo != null) {
                String root = System.getProperty("user.dir");
                String path = "src/main/resources/static/archivos";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();

                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];
                List<UsuarioDireccion> usuarios = null;
                archivo.transferTo(new File(absolutePath));//Guarda archivo
                if (tipoArchivo.equals("txt")) {
                    usuarios = this.LecturaArchivoTXT(new File(absolutePath));
                } else if (tipoArchivo.equals("xlsx")) {
                    usuarios = this.LecturaEXCEL(new File(absolutePath));
                }
                ResultFile resultFile = new ResultFile();
                resultFile.errorsFile = this.ValidaArchivos(usuarios);
                if (resultFile.errorsFile.isEmpty()) {
                    //Si es vacio se guarda la ruta del archivo 
                    resultFile.pathFile = absolutePath;
                    result.correct = true;
                    resultFile.errorsFile = null;
                    result.object = resultFile;
                    return ResponseEntity.status(201).body(result);
                } else {
                    //Es vacia no se guarda ruta
                    result.correct = false;
                    result.object = resultFile;
                    return ResponseEntity.ok(result);

                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping("/CargaMasiva/Procesar")
    public ResponseEntity Procesar(@RequestBody ResultFile resultFile) {
        Result result = new Result();
        try {
            if ((resultFile.pathFile != null) && (!resultFile.pathFile.isEmpty())) {
                String[] splintArchivo = resultFile.pathFile.toString().split("\\.");
                //toma el ultimo valor 
                String tipoArchivo = splintArchivo[splintArchivo.length - 1];
                List<UsuarioDireccion> usuarios = null;
                if (tipoArchivo.equals("txt")) {
                    usuarios = this.LecturaArchivoTXT(new File(resultFile.pathFile));
                } else if (tipoArchivo.equals("xlsx")) {
                    usuarios = this.LecturaEXCEL(new File(resultFile.pathFile));
                }
                for (UsuarioDireccion usuarioDireccion : usuarios) {
                    this.usuarioDAOImplementation.Add(usuarioDireccion);
                }
                result.correct = true;
                return ResponseEntity.status(201).body(result);
            } else {
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    private List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        List<UsuarioDireccion> usuarios = new ArrayList<>();
        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombre(campos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
                usuarioDireccion.Usuario.setUserName(campos[3]);
                usuarioDireccion.Usuario.setEmail(campos[4]);
                usuarioDireccion.Usuario.setPassword(campos[5]);
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                usuarioDireccion.Usuario.setFechaNacimiento(formato.parse(campos[6]));
                usuarioDireccion.Usuario.setSexo(campos[7]);
                usuarioDireccion.Usuario.setTelefono(campos[8]);
                usuarioDireccion.Usuario.setCelular(campos[9]);
                usuarioDireccion.Usuario.setCURP(campos[10]);
                usuarioDireccion.Usuario.setImagen(null);
                usuarioDireccion.Usuario.setEstatus(Integer.parseInt(campos[11]));
                usuarioDireccion.Usuario.Rol = new Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(Integer.parseInt(campos[12]));
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(campos[13]);
                usuarioDireccion.Direccion.setNumeroInterior(campos[14]);
                usuarioDireccion.Direccion.setNumeroExterior(campos[15]);
                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[16]));
                usuarios.add(usuarioDireccion);
            }

        } catch (Exception e) {
            usuarios = null;
        }
        return usuarios;
    }

    private List<UsuarioDireccion> LecturaEXCEL(File archivo) {
        List<UsuarioDireccion> usuarios = new ArrayList();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
                    usuarioDireccion.Usuario.setUserName(row.getCell(3).toString());
                    usuarioDireccion.Usuario.setEmail(row.getCell(4).toString());
                    usuarioDireccion.Usuario.setPassword(row.getCell(5).toString());
                    usuarioDireccion.Usuario.setFechaNacimiento(row.getCell(6).getDateCellValue());
                    usuarioDireccion.Usuario.setSexo(row.getCell(7).toString());
                    //Se convierte un string                    
                    DataFormatter dataFormatter = new DataFormatter();
                    usuarioDireccion.Usuario.setTelefono(dataFormatter.formatCellValue(row.getCell(8)));
                    //Se convierte a un string                 
                    usuarioDireccion.Usuario.setCelular(dataFormatter.formatCellValue(row.getCell(9)));
                    usuarioDireccion.Usuario.setCURP(row.getCell(10) == null ? "" : row.getCell(10).toString());
                    usuarioDireccion.Usuario.setEstatus((int) row.getCell(11).getNumericCellValue());
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol((int) row.getCell(12).getNumericCellValue());
                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(13).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14).toString());
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(15).toString());
                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(16).getNumericCellValue());
                    usuarios.add(usuarioDireccion);
                }

            }

        } catch (Exception e) {
            usuarios = null;
        }
        return usuarios;
    }

    private List<ErrorFile> ValidaArchivos(List<UsuarioDireccion> usuarios) {
        List<ErrorFile> listaErrores = new ArrayList<>();
        if (usuarios == null) {
            listaErrores.add(new ErrorFile(0, "Objeto null", "Posible error a la hora de leer archivo"));
        } else if (usuarios.isEmpty()) {
            listaErrores.add(new ErrorFile(0, "Objeto vacio", "Posible error a la hora de leer archivo"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : usuarios) {
                if ((usuarioDireccion.Usuario.getNombre() == null) || (usuarioDireccion.Usuario.getNombre().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Nombre vacio o null", "El campo nombre es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getApellidoPaterno() == null) || (usuarioDireccion.Usuario.getApellidoPaterno().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Apellido Paterno vacio o null", "El campo apellido paterno es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getApellidoMaterno() == null) || (usuarioDireccion.Usuario.getApellidoMaterno().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Apellido Materno vacio o null", "El campo apellido materno es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getUserName() == null) || (usuarioDireccion.Usuario.getUserName().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "UseName vacio o null", "El campo username es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getEmail() == null) || (usuarioDireccion.Usuario.getEmail().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Email vacio o null", "El campo email es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getPassword() == null) || (usuarioDireccion.Usuario.getPassword().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Password vacio o null", "El campo password es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getFechaNacimiento() == null)) {
                    listaErrores.add(new ErrorFile(fila, "Fecha de Nacimiento null", "El campo fecha nacimiento es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getSexo() == null) || (usuarioDireccion.Usuario.getSexo().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Sexo vacio o null", "El campo sexo es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getTelefono() == null) || (usuarioDireccion.Usuario.getTelefono().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Telefono vacio o null", "El campo telefono es obligatorio"));
                }
                /*
               // Intetar de resolver el casteo telefono
                Pattern pattern = Pattern.compile("^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
                Matcher matcher = pattern.matcher(usuarioDireccion.Usuario.getTelefono());
                if (!matcher.find()) {
                    listaErrores.add(new ResultFile(fila, "Telefono incorrecto" + usuarioDireccion.Usuario.getTelefono()+"k" , "El campo telefono tiene que ser de tipo texto"));
                }
                 */
                if ((usuarioDireccion.Usuario.getEstatus() > 1) || (usuarioDireccion.Usuario.getEstatus() < 0)) {
                    listaErrores.add(new ErrorFile(fila, "Estatus incorrecto", "El campo estatus solo puede ser 0 o 1"));
                }
                if ((usuarioDireccion.Usuario.Rol.getIdRol() < 1) || (usuarioDireccion.Usuario.Rol.getIdRol() > 3)) {
                    listaErrores.add(new ErrorFile(fila, "IdRol incorrecto " + usuarioDireccion.Usuario.Rol.getIdRol(), "El campo IdRol no es el id de los roles existentes"));
                }
                if ((usuarioDireccion.Direccion.getCalle() == null) || (usuarioDireccion.Direccion.getCalle().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "La calle es vacio o null", "El campo calle es obligatorio"));
                }
                if ((usuarioDireccion.Direccion.getNumeroExterior() == null) || (usuarioDireccion.Direccion.getNumeroExterior().isEmpty())) {
                    listaErrores.add(new ErrorFile(fila, "Numero Exterior vacio o null", "El campo Numero Exterior es obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

}
