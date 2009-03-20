<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.Authentication" %>
<%@ page import="org.springframework.security.GrantedAuthority" %>
<%@ page import="org.springframework.security.adapters.AuthByAdapter" %>

<%
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) { %>
			Authentication object is of type: <%= auth.getClass().getName() %><BR><BR>
			Authentication object as a String: <%= auth.toString() %><BR><BR>

			Authentication object holds the following granted authorities:<BR><BR>
<%			GrantedAuthority[] granted = auth.getAuthorities();
			for (int i = 0; i < granted.length; i++) { %>
				<%= granted[i].toString() %> (getAuthority(): <%= granted[i].getAuthority() %>)<BR>
<%			}

			if (auth instanceof AuthByAdapter) { %>
				<BR><B>SUCCESS! Your container adapter appears to be properly configured!</B><BR><BR>
<%			} else { %>
				<BR><B>SUCCESS! Your web filters appear to be properly configured!</B><BR>
<%			}

		} else { %>
			Authentication object is null.<BR>
			This is an error and your Acegi Security application will not operate properly until corrected.<BR><BR>
<%		}
%>
