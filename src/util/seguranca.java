package util;

import java.security.MessageDigest;

public class seguranca {
    
 public static String gerarHash(String senha){
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            
            //Convertendo os bytes para texto hexa-decimal (letras e números)
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (Exception e) {
            throw new RuntimeException("Erro ao gerar a hash: " +e.getMessage());
        }
        
    }
    
}
