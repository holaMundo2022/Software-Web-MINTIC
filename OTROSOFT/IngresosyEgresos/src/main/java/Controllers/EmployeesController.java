package Controllers;
import entities.Employees;
import services.Interfaz.IEmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/employees")
public class EmployeesController {
    @Autowired
    private IEmployeeServices employeeServices;
        @RequestMapping("/OE")
    //public String viewHomePage (Model model) {
      //      List<Employees> employeesList = employeeServices.listAll();
        //    model.addAttribute("employeesList",employeesList);
          //  return "index";
        //}

    @PostMapping
    public ResponseEntity<Employees> save(@RequestBody Employees employee) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeServices.save(employee));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> delete(@PathVariable("Id") Long Id) {
        try {
            if(employeeServices.findById(Id) != null){
                employeeServices.delete(Id);
                return ResponseEntity.status(HttpStatus.OK).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/hola")
    public String Saludo() {
            return "holaMundo";
    }

    @GetMapping ("/{Id}")
    public ResponseEntity<?> findById(@PathVariable("Id") Long Id) {
        try {
            if(employeeServices.findById(Id) != null){
                return ResponseEntity.status(HttpStatus.OK).body(employeeServices.findById(Id));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
