<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Login Form</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<script type="text/javascript" src="js/notify.js"></script>
<script type="text/javascript" src="js/notify.min.js"></script>

  </head>
 <style>
 body#LoginForm{ background-image:url("img/bg1.jpg"/* "https://simplecore.intel.com/itpeernetwork/wp-content/uploads/sites/38/2017/09/iStock_18336050_LARGE-2016_09_07-11_18_09-UTC-1920x1080.jpg" */); background-repeat:no-repeat; background-position:center; background-size:cover; padding:10px;margin-bottom: 66px; height: 590px;}

.form-heading { color:#fff; font-size:23px;}
.panel h2{ color:#444444; font-size:18px; margin:0 0 8px 0;}
.panel p { color:#777777; font-size:14px; margin-bottom:30px; line-height:24px;}
.login-form .form-control {
  background: #f7f7f7 none repeat scroll 0 0;
  border: 1px solid #d4d4d4;
  border-radius: 10px;
  font-size: 14px;
  height: 50px;
  line-height: 50px;
}
.main-div {
  /*background: url("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTEhMWFhUXFxgbFxgYFxgVGBcYGBcXGBoYHhgYHSggGBolGxgXITEiJSktLi4uFx8zODMsNygtLisBCgoKDg0OGxAQGy0lHSUtLS8tLS0tLS0tLS0tLS8tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAKgBKwMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAADBAECBQAGB//EAEMQAAEDAgIGBgcHAgUEAwAAAAEAAhEDITFBBBIiUWFxgZGhscHwBRMyUmJy0SMzQrLC4fGS0gYUgqLDU2OT4kNzg//EABkBAAMBAQEAAAAAAAAAAAAAAAECAwAEBf/EACwRAQEBAQABAgMGBgMAAAAAAAABAhEDEiExQbEiUXGBkbITMkJh8PGhweH/2gAMAwEAAhEDEQA/APk7dGJn2eOMHm3Fp4jer/5Z1icsDrDWbydMOHAwtT/Dmlaten6zRnOZrAEEOJgmMYkDkgekNKqF5cyiWCTA1HEieJXvfwvH6e9cdu+gUNBc4wG3OUWd/pxB4tngiVdCIcWuabE2MktM5OAkD5gqaPX0gGwcLHBmcG+Ch1fSCSTr3x2M9+Cn9j+5bN/2MVaeDib6o2hAPI/gd0kE5rmNAwtOIAs4cWHH/SSor6RWaGkyNkYtHHCQqN05+eqd9u2AjJlK52K2AIEapOBlzCeftMPmURoB2YkD8J9oDe127PwzQ26ZvYJzjMdMyEZlVlrERhF45XB6CU0k+9O9+5BvAJn3X5iPwnzbKyvJMuwe32vimxMZ8f5RqYYZ5Xi3SNaAI5/tLdHwIMkdII58somE3ppLqB6sEtiA7sP7HsPFMejmgvZrEDVdiYgThM2gOiZyKvW0OZLYAJlom4NrXAsZgdA530Ki71jTGd5AcARvEEbjBxW5U7ucLtbc7nDvv+YLmjZB3H9x4ogYQL4g+P170T1dyN4kd/1C3C2ops2iN9x1g9y0NPb92eDJ/wDHT+qVpNuw8YPd3FaXpOjs/L6vtpUwe0IyJ3XvxkBmyRxHijObf/R+lF9Vd18RPc7ulEdSEtvi0jPe5q3CXZMt2W8z+lWey9Tn+pGYyzfmP6FzhZx3uH6j9FuN6gWs2mcAD2koWrsniR4k+CdcyHOPutjpjV+p6FT1Pst5udwB/YA9K3A9RdrNto90Du1u8rtO0Q0i5hIcdaJaZBjGDzI6kRws53vGB1yfAdKtVoAR8IM8wcOsgdCHDTRMja4MHaP/AGKmo37JkSXPe8cNUFhA6XEnoCksMRm49n8z1I9RgaynUgES4AZmC0QeiT/qQ4ea+BLSwBUdIswkAWuZ4WuZPIJe+/afdx3Nx7cTwhPekHNdUcWs1GSXaowbP4fDrSlRvWbk7m49H8b0OKygl1sNkHZbvPvHf5GCJT0ggkk3Aguz+VuTRxCo4buTeA3+eKFujo8XHzlwTZvL1TkrUHp6s1paHkb844X9p3P+EK/pisTOuZjnqjeTmeGHDcq7+OPxHghOH1vn8R4J/J5bqDnw+P7oZf6b0jD1rsMzMDeT4IDvTFf/AKjpO+JPxExPnrAR9efE8OCG8fvl1xhyC5q6M+Lxz+mfoKfSlabVHXxg6ped5IvCXdptT/qP4w4jWO4AZedyhzf38jLgFQs8+HDzdJVs5xPlA6lRzjJcSRmSTqjcOKHHwjpN+9GLBv8APBVhm/sScVljtA+8Z8zfzBVY3Dz5CY0M/aUzqtEPbv8AeFseaGCIy7ZTS1rVqAv0O/KUWiyV1Jw6gd99kojHi8ADrsqYsl90dV6f0j/hyozRaVWWarwzV22g29bIIJG8LCGiO+H+tn1WhpmlTSaJtFO1/wDvWSFNoIdwA3bxir+S5vwQlvzWZorvht8bMOtFZox+H+tn1Qg1GpNggwDwvCkXVFpaK7ItwP424RcYq7dGPw/1Nw60ShSLySGtENOVrCJ4GF1MwfZBwxBy5HApkbR9Fc9k6rokEEa7Yc0gggibpkU3ANcHggmDJZiIOBJnEQTuO5KuMxsgRzvYC88u1G0ZxaQYBE4OnVdGRAxR7UdCMqPGY43bJHPzkiB7vht8tuIUv0bVa1zSHNIiYuDA1mkZQTAKqDaIGOOfJH1I3gzTgNVuN+eRsVqekjYy0ezSBub/AGdPHsvwWdSIDRs5kThkM84xWtp7hquGr+GiZm/3bLckc33JP5vyv0Y4IkGDbjMjMc7lGNMRM+y4dRP7dqvrt9zEkiHYTa1sBxTL3NLCSwj2cHATJPw8Fo2fdntpEaoPvf2qtKlMDeT2AfVNl1OfYd/WP7Fegad9l+Dh7YzaZ/Agn2kHUjgcS6/R/Ku2iTJH4j2fT6LS0h9LXcNR5yn1g7NjzdbGhaRo/qn61N2sRA2gY4WaLIwm/JcvImZtY4A+6B3fyraRIYzAt2tTdYxrHfnjxTmkVKcnZJt7wxjH2fZm8IOmAAATJl2UYOi4ysMBvCF+J867xnk8Jm24u+g5ckQNc4NaBIBOqAJgmJ1RmbC5nBFpAOdAbjEDvy58l6Oo7RqejlsfbHEg2Ax1eB3ppR15PS8jpZa2WNAgOubkEiQDBxONuPNJVYvF8797voP5eqvbI2QQMpPmeKVeW+6f6rYcscblC+1dOKVcJnt48TuHBAf5489w4J1+rBgEcyDu4Y+YQ3PZ7hw9/OMcN+SWr50QePPnLcFTVx/nr8wExUMbkMus65wGFs0lWzSxZ0ee/ihOhXeUN54efPclq+Q3OQnHirzjb9kF6SqxVxVdYcV0Xwnhv4KXUjuiw3buJU6rBvRryKjIMS5oPEawt3dSG3kr6APtGfO38wUU08ajUW3PI8MldoVtFaDOM6p4j9k1oNDWMZW6F0eLx+q8Q3eGtLp/ZM+Wl/zJaiYDuIHeF7307/hYM0VlT1jYDKM3mPvJNhvcvFPoEa0XECSL2kY7rwnvjnOyubPkmgmcgjhUbRN4GGKZqMPkJC6o2it1rBpJAk3A7wndD0I1qradNhc9wkDXaMG6xMkQLAm6QoOI9kkGCOjNaOh6a+hVbVpWc0QJAIu3VIINiIkI9vPZO69/9Buphri1zHS0kEFwxBgj2U0NTVZLXRLvxDh8KWe8vcXOMucSSd5JknrWnR0KW05BAcXAumZgjLKPFHtR35Of6halUABgOuII1hf/AG779ARGkYwesf2qGMINgjUSQbgcbCe1bqN8n+ew7nfZsN8XZi3s8E/p8Q7H2KOf/bZwSVRhERBaZLTAk5XGR4fVaumTqOs32KOQn7tnYtm+5Mbvr/K/RjB3zdf7ItQi4veJw3Im0J2RbHYb9ESs8g4NwH4G7hwR77J/xLYUDG/F1D6o+jtp3nWmDkLWPFWFU7m/0N+iLQqmfZbg78Ddx4LEltqNLoN9Y4E1JnAtbI6nQtHR6FAUjLna+QIA8UM6UWVS71cAHBzGz0mEDTnuqOBgiZtEAfslR33V4x6wE/t+670jTbOy7Wlz5t8RjrEHpUuYQcE1plMMAi73a2tY7Bky3nGO6FqvLwjTr+rbs2ecTPsjdz7u5auDqNda5dncRGPh0q72HGLb0Oo0xGrhM4zfeMB1LKwk8IL2eemPBOOGaFWFm8v1OWXzSxFjjcZYe1nwQq1EajXA3JdI5R14pylRc7ZBMuFh7x1sOyehLVmnVFsCe2PogtmkqouVRrdl/R3/AMdaNVYULUMGBaBJ3Cd+AvvS1fNKOCEU6aQzN5waJtmZw70vVpEfhjA554dCWr5pZwxQrZiexHc3FAeElWyHrEYEjyfqetCRXBV1TuSVWGPR33tP/wCxn5gqsFkT0f8Ae04P42cPxBVpkxZPC6Go2mRiCjUXxgh0nG/IojX7wO36quNXPvEdN3R9NcdGrAk+3R/5FnC8lG0Qn/L1vnpf8ipRGy7iBnxByPLFV15Na+Ln5J1cvuZvfPFWaiUaBLXusYjMWvzXMCQmqa0JzmkubJ2XC24jE8Bj0Lhco3o2iHkgv1YaSCTEkYCfOCuIBNzO/A96KGqiivY+gK1AOa1wc4y4RMasmAQeWNl5Wk0WOsRfh9VstJ9XT1jqtDn6rm6sk2JBOvlIha/Djl8s6rpuizLqe03Gwu0by3EDjhxSTQm6BaGlwLg4ObBAAIkPnB3BWqVWkWkOtJADWnmAceXUilLz2RSfDQIsZnosCOIkrV01uy7dqUSDv2GBZ3rDqNE2l3bq55iy1arjqVBlqUOY2W3C0+LeP+f8tfRluZbq7k3pOqWt97A8tVsd56lFWmYv8MHeAIHYFauCDjk3P4QjEe9zfxgA0c72/wBTfqi0NHO8YO/E33eapqpnQaZLtUYkO7QUTY1n1T2/5/8AC+nU5e4h+sMnOcJNs7qHU4DJLSL2BEifr4J709SLazwdW8EasQRAuEvUBAYdWDBgnog7p+qWWchdWS84z2tLSZAnDIxv6e5d6QoNaGw4Ou6wIMXAxBz8FJB8wradRLSHTZ0kb4nMxE8lr8TSst4VqzG+rZF3S8u4CGx3OKs5ql9LZZvJdhBJiIsDOMoVSVn1W4ckCply8Sn69IwJMEWg4/t0pUmN3YceCK2aG2dQw0GBc5t2hcHL90g8LQe8wdsi0wLA7Q2YHSehJVHHf/CC+AfWRgATxv2YdaDWl1ycBbrhGe0lVDTDgTFhmfe3DnmhV8kHoLk1UHegl5GaSx0Zpd2BQnNKO4m/8IRlLVsg1MOtBKM+VQt4hJVoNoH3lP52/mChqnQT9oz52/mChiMCj0Tjy8QjPxKBTKar09VxEgxmMME8S0d0T7it81L/AJEKlgfM3RNE+5q/NS/5EOmU6F+Zg4mBmUzows75fEJWE5oYEPn3d05jq5opaWoUnOs0EwJMAmBvtkmHi/ncu9G6X6pxMawLYIyMkG+8WwUBFDQrFq1aJFOmS4lhc/Vbe0ESRvnzmsti0fUjVY4OO05wItsxqmbm863DBZDSKUwRvIPVP1RWUjuKA1HYmQ0d9S7VbYxJjhh1LXqaO4037J9mjl8LFiNwA5+C13+xU+Sj+Vq0hPH31/lr6UJ1BxFmu/Dl8JU6TSIdcEWbjb8IQx7J5t/KUau2/Q38oRQ/pv4z/sDVRdHbc/K78pUhqJo7bn5XflKIeO/bhJzOSPXpO1aes8ap1oAMluE24oTgr1dWGEHavrCMIsOeaFCEHFRp1J06xBhxJaTnecentUvXaVpLnNa1xkMkNwtP8BDiuSL0OpEWxvPZF9+I6AiuQqmAwxPPJbisBrNsOjL5ko5P1GS3FuQznPh5slXUx7zf939q3Fsh7GqdYGdXYjfrXnoST41eM9kFel9C6FQeysKlSmHGn9nIdiHSbltrCF5rSGwVuL+PU7ws9UJgHiB+ab9Su5DPnrSOjIFOmXODRi4gdZhLVBdP6G77Rl4228M0ppPtO5nvS1fJc4FAcjOQXJKvkN+A6fBCRXoaSqwXRPbZ8ze8LmLtE9tnzN7wuajBozEViHS8EWliE8TrQ0b7mr81P9aGxW0f7qp81P8AWq08D5zVOIfeO1O6FMPgE7BmDECRc7xwScd60/ROiCo2sSY1KRcLxJBbbjy5Io6E9FaF61xbrAENkC0uMgBoki5nsVGoWi6Q5hlji0kQSCRY5WRRim4hodi0BQAYxwJJc5wLYw1dWOc627JZzFtVvRhZSZWn2nYG0XdF8zsnsWqGilPFOV9HdTe5jvaaYOaVDYcconj2ozqhcSXGSbknElFDQ7TYLZP3dX5KPc1YrQbcVr//AB1flo9zVieOfb/LX0oDPZPNvc5M6R7Q+VvcEvTdDSR7zO5yl1UkycUXP37Ng8bIPEjqj6qrakYbiOsQiGmfVg5ax7mpUuWSz8exVyvWjUZswdqTvwi2SE5yNW0kFjGBgBbJLs3a2HKEKeM9yL6Q0hjmsDWQ5s6xgCTbrwOO9AJTHpPQDTDXFwOvrSJuCDnzBB6Vl8ZvOst6C/BFeUOr7I6c/DJFTKr/ALvEY4Z54cPqEk5aFRp9VOqDf2sxlHI+AWe4I10cBe6EtUTJxHnNLPS1XID0KLoj0Nw7kvF8o0L71mW23fv4XSulOl7jvceGe7JN6AJqs2gzaG0YtBmbkJTS2gPcAdaHESMDBx6UlXyWcguRnIDklXyG5URHIaSqwTRfbb8w7wpC6g7abYe0O8KWkxw5IwaNRGPJEpYhDpC5scDh5wRqTDI5808Tp7RyPVVMcWfqUUTYxuvhvB74VaH3b+bO9ytRfZ3ED8zT4KqFnxGkSeZRWO5oDahw6xA7kVh8winqNf0JoLaznNLmthjiNZwZrOwAk8b8gUJ42iOKXFE5255dCdbTJcSZxvs36plN6ahrKGlem0zT2v0SlSbUc5zakuBbGoSH6oacAMzmSeCwqdMGQJN8dQ27U0yg5zgwOMawAlsNEnnAxla5R1LJ8v1iah2z09yhhU1mO1zY9XBWZQf7p6lnLeGNcwMv5W5QANCuSYhtGOJhvgsT/LPhpDXXByO9aTZ9VWnAClwvAtzQ43h5d/lr9tCkapjez8rkMOV3OOqTGdOBFo1XwAgFxRjl4bOlO1dWbbkEuQtdSH8EQ9PEly0NL0um6nQY1m0ydfZG1PHEpBoPuhGqUzI2RrRx2uEe9394sGXnsQBuo9ISKj5953ecFYVb+w3t+qP6T9JF8NLGkMLgDfN2NjyWVz8OMl5Q6j7dPRgO1NM0sAz6tnSHHsLkvUqGGxjciwEScjngipmVNWPUjagyYbvvcnlbrWY5ab6xNPVLATM61pFzaeMZ7kk+kZxaLDEhuXUEbHTzt/Qu9hseAOW9KPw6T4JurSdIw3Djc9eaBUpGMsT4Lemq5zSb1Qi3Qe9Fqgt8gobHC8idkxlfoS2LycH9AVKTdJpGvHqg6Xa3s4GJi8a0SkvTLqZr1jS+7NR+pGGprHV7IQ3uyQCJUrPdbKmR6Eu5MFp3buKCSfIS2LZBchozpzG/rQtbgOpSq0W0c7TeY71cOuhUnXHMKzVpRpmlfGbAomjtlwG8jD90PR3X6D3FFo1HTIdEZybdV1SJ01TENeOLe8qKb8ROIg8bg+HYr0qwDHXc64xkAi82BnMqtFxh0GBFwLSNZuO8THUqI02+kRckCSYGeO4YdMKWm9pSzXorHFNKlY9D/hljX1C14aWRtEu1C0ZuB1gT2wLwYWdgUsHmcUdzrlNErDDCtDQD9syPfHD8Q3YLLa9PejH/AGtO59puFjiMOKyG57ND0rQNOq5pgEHwS9M+PcqVdMc5xJcbziciiUH7W0TF5i5wOHFZz8sz7mgSWC1m58zMDjin9Hqk0KkzA1AMgNomPFZtWtMasht9VszAtc8TmntBrkaPWAdAmmYn4pC0P4M/av4a/bVW6QWyABB1ZkA3AxvzPWrDSz7rP6G/RKsc44HKMRgiig+CbQMdppx4TKzjuYONLPus/ob9FP8Amj7rP6WpDXUiojyN6I1dG0sgyW0/6WhF9K6T61wIa0WGBaFjveRBtcbhyPniq068EHwEdoK3sE8ee95/n6CPBLiTEiCeW+3WjeltC1Gsq6wd63WdhEQRxv7SRdWk7sOwASo0wzORaYInPMgZXxA7sBZ7q8kvsWc5Eq1PsWib67oHAtZPaOxANU+ROUKDVAblJJm14tGIgDHC6PFJlMP9W4gbALdbgZcG/mPWlq74dgDsjHi0J6jpVMaPVYS/1jnMLQPZIaTMmbG84fhCz9PMOw/Cz8jUYrIE95AEZg5JWobDme5qLVeLRuvz8wqVtIOoAJEExfK31Ky2YG5khtwLZ2zOeA6Uu90Y+6R1k9aJVqmG3yPeR3Jc1HCbkSOsG/Utp089wHoLk1667SZHFtiLlLvqHfbKfPFS0rkEoTnFFLzG9Ae5T0tIh5t1+H0QiVZxVJUqrFqIMiN6swriwtsSMbwQRPRmpps4jzZCDRqZE5gX48vBWaUTRhqkmGuEOEEziMbc7IbW+SremydTrU9G6M17X61RrIAMum+NhAJlWp0GgO+1bhez/eHw70hTOyehEpixno6x4KnqnJOI2GhTGVRp6Hf2q4A3jt+iXptG+PPBGa0WuL9i0Tsbn+GtDbVqOD6bqjGt1napcC1oIlwDRLnZAb3CbSs4OCEAQTDh0E3VmtOKaJ0w1wTGj3cBIEkY4dM5JVotjujG/YjaO2XATFxe8ATiYBMDhdMlqG6QJdvAIki4F4nlKvrRhhh585oTK1w1uEiYka8OJDiJ4wEUwc4xtfITuzNue4I/JLU9h/Wu1WzMXjje/anNEf8AYVv/AM/zJNwGo2+8jHMwQd2Hd0N6G0f5eudYTNMAQTO1jOFlst4P5r+Gv20Avwvl4ld6070N5s35f1OVZEcVq59Z9xtZSHoE96jWQL6RnEixxHYqtqQVRxtJN5wvPPzvXADNwHQfos3FmOvfr6V6H/F40drKDaUesgOebgw9rXAu2YJJJOJhecpBpMF4HNp3jdMnHdghaUZe69pdGO8wOCFnbKfPzQWSCRiMRnF7jgM0OpUOo0WiTzm2PYr0nuZD2uhwiN/VEEb53qtSjLNYYD2hB2fZGthEEnLssiaFmvgovpXThVcHaobZogYbIAnsSjih6s5gY48BPbgm77KzE71Soe5BcUTjPRv8+KC9vxDtt2IL5jqpBDcrHliVTWu75IyOTVWrgOXiULfyQ1V+BVHYILiiOCE5TquYgGxjddBKLBvHTy+iHqcRhx42wx+qlpWBuYbxfkhSiOEZjPA7uSNS0p4AAe0DdDfopVSFwiNQvPnirN8+fFbN9xr0XoX0lQpU6gqUG1HOENJLhqHfskSkX6SDhTbjvfw+JJUwUVtIrs157rPp4jczphlYQdhvW7+5XbVHuN63f3YIbaYAufPnoVwR58VOSkozag9wdbvqih2GwP8Ad9exAa/cB9OKKK7rCf43mO5PE71t+gvRgr1NR+swESCGlwG9ziXANa0S43ySXqiMQ0c3fv2K3o70rUoF3q9WXjVIcxlQRINw8EEyB1JcaTnDbYWz3+eCacSsp2jRJMS3jc2G8nDrT1eqwHUpO+zIaXXcC8tna2hsi5scOKzKWmkAt1WRi7ZEngTjGFkXR9IcXNDWt1nOAFgLkwL8+5N7I6xR6cgggtMHeLkHATkrwb2nln1YAIA0sgnZZaw2cPN+tHoaVgNRkC52fPAJp7k1LxJqW5d58lN6PUilWHyD/cZ8UxXNRrGPcxm17JLQbTFpwv3IFP0gNSp9myNmIGJk3MyjM8Dw65rvPlfpYWNQnosOsn6ozaZM81zNLEgatPe4llhwEG+MdK0tC9KgM1TTpy/CAARfHhn1ISIbtZTjfpVQ7xUPdJBGboHZ9QhNm3OOm1kLBkPbPqZja1+yB56UsXDj56FZk6gEH2ntPAwwgHdcJYA5g7jl/GIQaZGaGkgTF8ZEDjghVRc8zuOCoQd3Dlu8FRx88eKx5FnhRJABwucxfCQRPs3HahvPncd3Bc87ItG0ZJ4AWxsLnzgTxLqQgOBneLAt6Jw4pV8b1dlSDMC2+T0EbkOsWydUHVynGOMGCgrlXZgzIORy6Rj0oDoVy4bh1nsM9iG92I1RbHHtE9oWUiphDDgrOeNw4XPYZt0oZcJ9nvnpE3S1WBuI3daEXjcO362RC4e6Ot0d9iq1QIBwN5zPUcUlVkUBmdkWEnHDrQnOb7vartpzhunq3HwKEQN/Z37lPVUmVS1vEdR+ioaI97sRNUb+ztt3hRqt39h8CpU6xDBgD0nvgWUipuA6vrgOJ6ly5bo8Xp1CcOOFurhxKt6yM+ZyncJxPErlyb1UORwcenHlx4lWa/PqHiuXJpSWCs3bseJ8+KKHZDE4nh4BcuTxOmdC0V9Uu9W0u1WucYya0S53IDvCo04DpPnqC5cmlTsXDrcSfPngj6PUh4IJGrFxMjiIIM54qVyMJYsHyb5yevmjaMS4wMXEDrMqVypLxLUaOn+kS4taCdRjYaOAm8bybniUgH2jeVy5PanMyfB3rPaO/wCs+CIKl+TT+UlcuSylsQH2b8x/Spe6z/mH6h4rlyIDGrsjjJ6mMPeED1vsk4XaeI/h3YuXLdCSKmoRN7tPZMEcRMW+IrvWGYBNxLL4fDym3OFy5Ho8D9facsDH4Tw+HhhiNyvUfstEXBdgbEQ32d1ssDOFly5bpuQv63DccDgORj2ei3BCc4XthiMxxjAjiIXLluqSKPcOF+o9JGPBwKA93ZwNuYxHMFcuS9VzA3Ovx7SN4ODhzQXHlw3H+0rlyTVVzAnO588xwO8eeCir7Lekx0zIK5cpWq5Ca0nC9ieBAx6UInDvzHA7wuXKVqsV8xu4hdHAHjMeK5ckM//Z"); *//* #ffffff none repeat scroll 0 0 */
  background-color:rgba(40,57,101,.9);
  border-radius: 10px;
  margin: 10px auto 30px;
  max-width: 38%;
  padding: 50px 70px 70px 71px;
     
}

.login-form .form-group {
  margin-bottom:10px;
}
.login-form{ text-align:center;}
.forgot a {
  color: #777777;
  font-size: 14px;
  text-decoration: underline;
}
.login-form  .btn.btn-primary {
  background: #1f5ce6c2 none repeat scroll 0 0;
  border-color: #14c7e459;
  color: #ffffff;
  font-size: 14px;
  width: 100%;
  height: 50px;
  line-height: 50px;
  padding: 0;
}
.forgot {
  text-align: left; margin-bottom:30px;
}
.botto-text {
  color: #ffffff;
  font-size: 14px;
  margin: auto;
}
.login-form .btn.btn-primary.reset {
  background: #ff9900 none repeat scroll 0 0;
}
.back { text-align: left; margin-top:10px;}
.back a {color: #444444; font-size: 13px;text-decoration: none;}
   </style>
   <%
   response.setHeader("Cache-Control","no-cache");
   response.setHeader("Cache-Control","no-store");
   response.setHeader("Pragma","no-cache");
   response.setDateHeader ("Expires", 0);
   %>
  <script type="text/javascript">

 $(document).ready(function() {
 	 $("#login").click(function(){
 		 var email = $("#inputEmail").val(); 
 		 var pwd = $("#inputPassword").val(); 
 		 console.log("email"+email+pwd);
 		 $.ajax({
             url: 'LoginServelt',
             type: "POST",
             data:{
                 email:email,
                 pwd:pwd
               },
              success: function(data){
              if(data === "success")
              {
            	  window.location.href = "session.jsp?email="+email;
              }
               else
              {
            	   $("#msg").notify("Please enter valid EmailId and Password");
             	/* var message="Username and password wrong please check it once";
             	$("#error").append(message);
             	window.location.href = 'Loginpage.jsp' */
              }
             } 
         });
 	 });
 });
 
$(document).ready(function(){
    $('#inputPassword').keypress(function(e){
      if(e.keyCode==13)
      $('#login').click();
    });
});


 
 </script>

<body id="LoginForm">
<div class="container">
<!-- <h1 class="form-heading">login Form</h1> -->
<div class="login-form">
 <img src="img/mainlogo.png" style="/* margin-top: -83px; */margin-left: 1000px; border-radius: 6px;">
   <a href="http://www.tryexa.com"><button type="button" class="btn btn-primary" style="margin-left: 1025px;width:100px">TryExa</button></a>
<div class="main-div" style="margin-top: 50px;">
    <div class="panel">
   <h4 style="color: white;">Login</h4>
   <!-- <p style="color: white; font-weight: bold;">Please enter your email and password</p> -->
<!--  <img src="img/mainlogo.png" style="margin-top: -83px;margin-left: 6px; background: #fafbff; border-radius: 6px;"> -->
   </div>
   	

        <div class="form-group">


            <input type="email" class="form-control" id="inputEmail" placeholder="Email Address">

        </div>

        <div class="form-group">

            <input type="password" class="form-control" id="inputPassword" placeholder="Password">

        </div>
        <div class="forgot">
        <!-- <a href="reset.html">Forgot password?</a> -->
</div>
        <button type="submit" class="btn btn-primary" id="login" style="border-radius: 10px;">Login</button>
	
    <div id="msg" style="margin-left: 6px;" ></div>
    </div>

</div>

</div>

</body>
</html>