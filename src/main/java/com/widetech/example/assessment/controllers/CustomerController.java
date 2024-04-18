package com.widetech.example.assessment.controllers;

import com.widetech.example.assessment.entities.Customer;
import com.widetech.example.assessment.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.Objects;


/**
 * Customer controller.
 */
@RestController  //not required to put ResponseBody
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * List all customer detail.
     *
     * @param pageNo    - page No. of returned paginated result
     * @param pageLimit - define the size of each page
     * @return return all customer detail with pagination
     */
    @GetMapping(value = "/getAllDetails", produces = "application/json")
    public ResponseEntity<?> getAllDetails(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name = "pageLimit", defaultValue = "50") Integer pageLimit) {
        try {
            Pageable testing = PageRequest.of(pageNo, pageLimit);
            Page<Customer> allCustomer = customerService.getAllCustomer(testing);
            if (Objects.isNull(allCustomer) || allCustomer.isEmpty() || !allCustomer.iterator().hasNext()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail to retrieve all! No customer detail found.");
            }
            return new ResponseEntity<>(allCustomer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail to retrieve all! An unexpected error occurred.");
        }
    }

    /**
     * View a specific customer by its id.
     *
     * @param id
     * @return Customer object found in database
     */
    @GetMapping(value = "/getDetailById/{id}", produces = "application/json")
    public ResponseEntity<?> getDetailById(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Fail to retrieve! Missing Customer ID.");
            }
            Customer custResultById = customerService.getCustomerById(id);
            if (Objects.isNull(custResultById)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail to retrieve! Customer with ID \"" + id + "\" not found.");
            }
            return new ResponseEntity<>(custResultById, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail to retrieve! An unexpected error occurred.");
        }
    }

    /**
     * Create new customer
     *
     * @param customer body
     * @return result with http status
     */
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            if (Objects.isNull(customer) || Objects.isNull(customer.getCustPhoneNumber()) || customer.getCustPhoneNumber().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Fail to create! Missing Customer Phone Number.");
            } else {
                Customer existingCustomer = customerService.getCustomerByCustPhoneNumber(customer.getCustPhoneNumber());
                if (Objects.nonNull(existingCustomer)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Fail to create! Duplicate phone number exists in the database.");
                }
            }
            Customer createdCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail to create! An unexpected error occurred.");
        }
    }

    /**
     * Update newCustData data
     *
     * @param id       - id of newCustData data to be updated
     * @param newCustData - body with new newCustData data
     * @return result with http status
     */
    @PatchMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer newCustData) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Update Failed! Missing Customer Id");
            }
            Customer custToUpdate = customerService.getCustomerById(id);
            if (Objects.isNull(custToUpdate)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Failed! Customer with ID \"" + id + "\" not found.");
            } else if (Objects.nonNull(customerService.getCustomerByCustPhoneNumber(newCustData.getCustPhoneNumber()))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Update Failed! Duplicate phone number exists in the database.");
            }
            Customer updatedCustomer = customerService.updateCustomer(custToUpdate, newCustData);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Failed! An unexpected error occurred.");
        }
    }

    /**
     * Delete product by its id.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Delete failed! Missing Customer Id.");
            } else {
                if (Objects.isNull(customerService.getCustomerById(id))) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed! Customer with ID \"" + id + "\" not found.");
                }
                customerService.deleteCustomer(id);
                return ResponseEntity.status(HttpStatus.OK).body("Delete failed! Customer with ID \"" + id + "\" deleted successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete failed! An unexpected error occurred.");
        }
    }

    //    /**
    //     * New product.
    //     *
    //     * @param model
    //     * @return
    //     */
    //    @RequestMapping("product/new")
    //    public String newProduct(Model model) {
    //        model.addAttribute("product", new Customer());
    //        return "productform";
    //    }

    //    /**
    //     * Save product to database.
    //     *
    //     * @param product
    //     * @return
    //     */
    //    @RequestMapping(value = "product", method = RequestMethod.POST)
    //    public String saveProduct(Customer product) {
    //        customerService.createCustomer(product);
    //        return "redirect:/product/" + product.getId();
    //    }
}
