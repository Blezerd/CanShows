<%--
 * dashboard.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">
	<jstl:choose>
		<jstl:when test="${option==1 }">
			<div>
				<display:table name="organisers" id="row"
					requestURI="dashboard/administrator/organisersWithCompetition.do"
					pagesize="5" class="datagrid">

					<display:column property="name" titleKey="organiser.name"
						sortable="true" />


					<display:column titleKey="organiser.competitions" sortable="true">
						<jstl:out value="${row.competitions.size()} ">
						</jstl:out>
					</display:column>

				</display:table>
			</div>
		</jstl:when>
		<jstl:when test="${option==2 }">
			<fieldset>
				<div>
					<h3>
						<a href="breed/details.do?breedId=${dog.breed.id}"><jstl:out
								value="${dog.breed.name}"></jstl:out></a> <br>
						<spring:message code="dashboard.name"></spring:message>
						<jstl:out value="${dog.name}">
						</jstl:out>
						<br>
						<spring:message code="dashboard.nickname"></spring:message>
						<jstl:out value="${dog.nickname}">
						</jstl:out>
						<br>
						<jstl:if test="${dog.sex=='MALE'}">
							<spring:message code="dashboard.sex"></spring:message>
							<spring:message code="dog.male"></spring:message>
							<br>
						</jstl:if>
						<jstl:if test="${dog.sex=='FEMALE'}">
							<spring:message code="dashboard.sex"></spring:message>
							<spring:message code="dog.female"></spring:message>
							<br>
						</jstl:if>
						<spring:message code="dashboard.weight"></spring:message>
						<jstl:out value="${dog.weight}"></jstl:out>
						<spring:message code="dashboard.weight.unidad"></spring:message>
						<br>
						<spring:message code="dashboard.height"></spring:message>
						<jstl:out value="${dog.height}"></jstl:out>
						<spring:message code="dashboard.height.unidad"></spring:message>

						<br>
						<spring:message code="dashboard.totalPoints"></spring:message>
						<jstl:out value="${dog.totalPoints}"></jstl:out>
						<br>
					</h3>
					<display:table name="results" id="row" pagesize="5"
						class="datagrid">
						<display:column property="competition.title"
							titleKey="dog.competition" sortable="true" />
						<display:column property="position" titleKey="dog.position"
							sortable="true" />
					</display:table>
					<hr>
					<fieldset>
						<legend>
							<spring:message code="pedigree"></spring:message>
						</legend>
						<jstl:if test="${dog.father!=null}">
							<fieldset>
								<legend>
									<spring:message code="pedigree.father"></spring:message>
								</legend>
								<spring:message code="dashboard.name"></spring:message>
								<jstl:out value="${dog.father.name}"></jstl:out>
								<br>
								<spring:message code="dashboard.nickname"></spring:message>
								<jstl:out value="${dog.father.nickname}"></jstl:out>
								<br>
								<spring:message code="dashboard.totalPoints"></spring:message>
								<jstl:out value="${dog.father.totalPoints}"></jstl:out>
								<br>
							</fieldset>
						</jstl:if>
						<jstl:if test="${dog.mother!=null}">
							<fieldset>
								<legend>
									<spring:message code="pedigree.mother"></spring:message>
								</legend>
								<spring:message code="dashboard.name"></spring:message>
								<jstl:out value="${dog.mother.name}"></jstl:out>
								<br>
								<spring:message code="dashboard.nickname"></spring:message>
								<jstl:out value="${dog.mother.nickname}"></jstl:out>
								<br>
								<spring:message code="dashboard.totalPoints"></spring:message>
								<jstl:out value="${dog.mother.totalPoints}"></jstl:out>
								<br>
							</fieldset>
						</jstl:if>
						<jstl:if test="${dog.father.father!=null}">
							<fieldset>
								<legend>
									<spring:message code="pedigree.father.father"></spring:message>
								</legend>
								<spring:message code="dashboard.name"></spring:message>
								<jstl:out value="${dog.father.father.name}"></jstl:out>
								<br>
								<spring:message code="dashboard.nickname"></spring:message>
								<jstl:out value="${dog.father.father.nickname}"></jstl:out>
								<br>
								<spring:message code="dashboard.totalPoints"></spring:message>
								<jstl:out value="${dog.father.father.totalPoints}"></jstl:out>
								<br>
							</fieldset>
						</jstl:if>
						<jstl:if test="${dog.father.mother!=null}">
							<fieldset>
								<legend>
									<spring:message code="pedigree.father.mother"></spring:message>
								</legend>
								<spring:message code="dashboard.name"></spring:message>
								<jstl:out value="${dog.father.mother.name}"></jstl:out>
								<br>
								<spring:message code="dashboard.nickname"></spring:message>
								<jstl:out value="${dog.father.mother.nickname}"></jstl:out>
								<br>
								<spring:message code="dashboard.totalPoints"></spring:message>
								<jstl:out value="${dog.father.mother.totalPoints}"></jstl:out>
								<br>
							</fieldset>
						</jstl:if>
						<jstl:if test="${dog.mother.father!=null}">
							<fieldset>
								<legend>
									<spring:message code="pedigree.mother.father"></spring:message>
								</legend>
								<spring:message code="dashboard.name"></spring:message>
								<jstl:out value="${dog.mother.father.name}"></jstl:out>
								<br>
								<spring:message code="dashboard.nickname"></spring:message>
								<jstl:out value="${dog.mother.father.nickname}"></jstl:out>
								<br>
								<spring:message code="dashboard.totalPoints"></spring:message>
								<jstl:out value="${dog.mother.father.totalPoints}"></jstl:out>
								<br>
							</fieldset>
						</jstl:if>
						<jstl:if test="${dog.mother.mother!=null}">
							<fieldset>
								<legend>
									<spring:message code="pedigree.father.mother"></spring:message>
								</legend>
								<spring:message code="dashboard.name"></spring:message>
								<jstl:out value="${dog.mother.mother.name}"></jstl:out>
								<br>
								<spring:message code="dashboard.nickname"></spring:message>
								<jstl:out value="${dog.mother.mother.nickname}"></jstl:out>
								<br>
								<spring:message code="dashboard.totalPoints"></spring:message>
								<jstl:out value="${dog.mother.mother.totalPoints}"></jstl:out>
								<br>
							</fieldset>
						</jstl:if>
					</fieldset>
				</div>
			</fieldset>
		</jstl:when>
		<jstl:when test="${option==3 }">
			<div>
				<display:table name="judges" id="row"
					requestURI="dashboard/administrator/judgeWithCompetitions.do"
					pagesize="5" class="datagrid">

					<display:column property="name" titleKey="judge.name"
						sortable="true" />


					<display:column titleKey="judge.competitions" sortable="true">
						<jstl:out value="${row.competitions.size()} ">
						</jstl:out>
					</display:column>

					<display:column titleKey="judge.results" sortable="true">
						<jstl:out value="${row.results.size()} ">
						</jstl:out>
					</display:column>

				</display:table>
			</div>
		</jstl:when>
		<jstl:when test="${option==4 }">
			<div>
				<script> 
					var addresses = new Array;
					var latitudes = new Array;
					var longitudes = new Array;
				</script>
				<jstl:forEach var="j" begin="0" end="${calles.size()-1}">
					<script>addresses.push(${calles.get(j).toString()});</script>
				</jstl:forEach>
				<script type="text/javascript">
					function geocode_result_handler(result, status) {
						var marker;
				  		if (status != google.maps.GeocoderStatus.OK) {
				    		alert('Geocoding failed. ' + status);
				  		} else {
				    		map.fitBounds(result[0].geometry.viewport);
				    		var marker_title = result[0].formatted_address;
				    		if (marker) {
				      			marker.setPosition(result[0].geometry.location);
				      			marker.setTitle(marker_title);
				      			market.setAnimation(google.maps.Animation.DROP);
				      			map.setCenter(new google.maps.LatLng(37.3876175, -5.9765625));
								map.setZoom(11);
				    		} else {
				      			marker = new google.maps.Marker({
				        			position: result[0].geometry.location,
				        			title: marker_title,
				        			animation: google.maps.Animation.DROP,
				        			map: map
				      			});
				      			map.setCenter(new google.maps.LatLng(37.3876175, -5.9765625));
								map.setZoom(11);
				    		}
				  		}
					}
					var map
					function initMap() {
						var geocoder = new google.maps.Geocoder();
						map = new google.maps.Map(
							document.getElementById('map'),{
								center: new google.maps.LatLng(37.3876175, -5.9765625), 
								zoom: 11, 
								mapTypeId: google.maps.MapTypeId.ROADMAP
							}
						);
						for(var i=0;i<addresses.length;i++){
							var address = addresses[i];
							geocoder.geocode({'address': address }, geocode_result_handler);
						}
						//Intentar centrar el mapa y poner zoom
						map.setCenter(new google.maps.LatLng(37.3876175, -5.9765625));
						map.setZoom(11);
					}
					
				</script>
				<div id="map"
					style="width: 900px; height: 600px; border: 2px solid brown; margin-left: 5%;"></div>
				<script asinc defer
					src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCBU3oKn-_FUn4pGKCf2iRWKjw_edjzQeY&sensor=false&libraries=geometry,places&callback=initMap"></script>
			</div>
		</jstl:when>
	</jstl:choose>
</security:authorize>



