
package com.example.apibancocrud.controller;

import com.example.apibancocrud.service.TransaccionService;
import com.example.apibancocrud.model.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    @Autowired
    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/deposito")
    public ResponseEntity<Map<String, String>> deposito(@RequestBody Map<String, String> request) {
        String montoStr = request.get("monto");
        String cuenta = request.get("cuenta");
        String codigoTransaccion = request.get("codigoTransaccion");
        Map<String, String> response = new HashMap<>();
        try {
            Movimiento movimiento = transaccionService.depositar(cuenta, new BigDecimal(montoStr), codigoTransaccion);
            response.put("codigoTransaccion", codigoTransaccion);
            response.put("codigoAfectacion", String.valueOf(movimiento.getId()));
            response.put("Mensaje", "Deposito realizado satisfactoriamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("codigoTransaccion", codigoTransaccion);
            response.put("codigoAfectacion", "-");
            response.put("Mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/retiro")
    public ResponseEntity<Map<String, String>> retiro(@RequestBody Map<String, String> request) {
        String montoStr = request.get("monto");
        String cuenta = request.get("cuenta");
        String codigoTransaccion = request.get("codigoTransaccion");
        Map<String, String> response = new HashMap<>();
        try {
            Movimiento movimiento = transaccionService.retirar(cuenta, new BigDecimal(montoStr), codigoTransaccion);
            response.put("codigoTransaccion", codigoTransaccion);
            response.put("codigoAfectacion", String.valueOf(movimiento.getId()));
            response.put("Mensaje", "Retiro realizado satisfactoriamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("codigoTransaccion", codigoTransaccion);
            response.put("codigoAfectacion", "-");
            response.put("Mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
