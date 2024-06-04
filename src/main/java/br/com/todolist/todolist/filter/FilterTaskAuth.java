package br.com.todolist.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.todolist.todolist.user.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/task/creating/")) {

            var authorization = request.getHeader("Authorization");
            var auth = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(auth);
            var authString = new String(authDecode);

            String[] credentials = authString.split(":");

            String username = credentials[0];
            String password = credentials[1];

            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
                return;
            }

            var passVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (passVerify.verified) {
                request.setAttribute("idUser", user.getUuid());

                filterChain.doFilter(request, response);
                return;
            }

           response.sendError(401);
        }

        filterChain.doFilter(request, response);
    }
}
