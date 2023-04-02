package com.webapp.tdastore.controller.web;


import com.webapp.tdastore.data.entities.Product;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.entities.Wishlist;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.response.CustomResponse;
import com.webapp.tdastore.data.payload.response.WishlistResponse;
import com.webapp.tdastore.security.CustomUserDetails;
import com.webapp.tdastore.services.ProductService;
import com.webapp.tdastore.services.UserService;
import com.webapp.tdastore.services.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wishlists")
@SecurityRequirement(name = "Bearer Authentication")
public class WishlistRestController {
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    private Authentication auth;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<WishlistResponse> getFavouriteOfUser() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        //Redirect to login if guest request
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new CustomExceptionRuntime(400, "The request has failed. " +
                    "Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        //Retrive user from spring security
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        List<Wishlist> data = wishlistService.findAllByUser(user);
        if (data.size() < 1) {
            throw new CustomExceptionRuntime(200, "Empty wishlist");
        }
        List<WishlistResponse> response = data.stream().map(t -> {
            WishlistResponse item = new WishlistResponse();
            item.setProduct(productService.mappingProductToResponseObject(t.getProduct()));
            item.setWishId(t.getWishId());
            return item;
        }).collect(Collectors.toList());
        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> addItems(@RequestParam String productCode) {
        auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        User user = userService.findByEmail(email);
        Product product = productService.findProductByCode(productCode);
        if (user != null && product != null) {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setProduct(product);
            //insert product into favourite list of user
            wishlistService.insert(wishlist);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CustomResponse(200, "Add product into wishlist has success",
                            System.currentTimeMillis()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse(400, "Product Code Or Email not exist",
                        System.currentTimeMillis()));
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse> deleteItems(@PathVariable long itemId) {
        auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        User user = userService.findByEmail(email);
        Wishlist wishlist = wishlistService.findById(itemId);

        if (wishlist.getUser().getUserId() == user.getUserId()) {
            //delete
            wishlistService.remove(wishlist);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CustomResponse(200, "Remove item  t from the wishlist was successful." +
                            "wishlist has success",
                            System.currentTimeMillis()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse(400, "Item code or missing jwt in request",
                        System.currentTimeMillis()));
    }

}
