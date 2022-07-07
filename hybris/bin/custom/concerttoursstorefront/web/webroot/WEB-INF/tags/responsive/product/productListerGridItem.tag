<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>
<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>

<c:set value="product-item" var="productTagClasses"/>
<c:forEach var="tag" items="${product.tags}">
	<c:set value="${productTagClasses} tag-${tag}" var="productTagClasses"/>
</c:forEach>

<div class="${fn:escapeXml(productTagClasses)}">
	<ycommerce:testId code="product_wholeProduct">
		<a class="thumb" href="${fn:escapeXml(productUrl)}" title="${fn:escapeXml(product.name)}">
			<product:productPrimaryImage product="${product}" format="product"/>
		</a>
		<div class="details">

			<c:choose>
				<%-- CONCERT TOURS --%>
				<c:when test="${categoryCode eq 'concerts'}">
					<div class="">${product.concertDate} / ${product.venue}</div>
					<a class="name" href="${fn:escapeXml(productUrl)}">
						<c:set var="bandAndTour" value="${product.tour.band.name} / ${product.tour.name}" />
						<c:out escapeXml="false" value="${ycommerce:sanitizeHTML(product.tour.band.name)}" />
					</a>
					<div class="">${product.tour.name}</div>
					<div class="">
						<c:choose>
							<c:when test="${not empty product.startingPrice}">
								<label><spring:theme code="product.variants.ticket.startingPrice"/>&nbsp;<format:price priceData="${product.startingPrice}"/></label>
							</c:when>
							<c:otherwise>
								<label><spring:theme code="product.variants.ticket.notAvailable"/></label>
							</c:otherwise>
						</c:choose>
					</div>
				</c:when>
				<%-- APPAREL --%>
				<c:otherwise>
					<ycommerce:testId code="product_productName">
						<a class="name" href="${fn:escapeXml(productUrl)}">
							<c:out escapeXml="false" value="${ycommerce:sanitizeHTML(product.name)}" />
						</a>
					</ycommerce:testId>

					<c:if test="${not empty product.potentialPromotions}">
						<div class="promo">
							<c:forEach items="${product.potentialPromotions}" var="promotion">
								${ycommerce:sanitizeHTML(promotion.description)}
							</c:forEach>
						</div>
					</c:if>

					<ycommerce:testId code="product_productPrice">
						<div class="price"><product:productListerItemPrice product="${product}"/></div>
					</ycommerce:testId>
					<c:forEach var="variantOption" items="${product.variantOptions}">
						<c:forEach items="${variantOption.variantOptionQualifiers}" var="variantOptionQualifier">
							<c:if test="${variantOptionQualifier.qualifier eq 'rollupProperty'}">
								<c:set var="rollupProperty" value="${variantOptionQualifier.value}"/>
							</c:if>
							<c:if test="${variantOptionQualifier.qualifier eq 'thumbnail'}">
								<c:set var="imageUrlHtml" value="${fn:escapeXml(variantOptionQualifier.value)}"/>
							</c:if>
							<c:if test="${variantOptionQualifier.qualifier eq rollupProperty}">
								<c:set var="variantNameHtml" value="${fn:escapeXml(variantOptionQualifier.value)}"/>
							</c:if>
						</c:forEach>
						<img style="width: 32px; height: 32px;" src="${imageUrlHtml}" title="${variantNameHtml}" alt="${variantNameHtml}"/>
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</div>


		<c:set var="product" value="${product}" scope="request"/>
		<c:set var="addToCartText" value="${addToCartText}" scope="request"/>
		<c:set var="addToCartUrl" value="${addToCartUrl}" scope="request"/>
		<c:set var="isGrid" value="true" scope="request"/>
		<c:if test="${categoryCode ne 'concerts'}">
			<div class="addtocart">
				<div class="actions-container-for-${fn:escapeXml(component.uid)} <c:if test="${ycommerce:checkIfPickupEnabledForStore() and product.availableForPickup}"> pickup-in-store-available</c:if>">
					<action:actions element="div" parentComponent="${component}"/>
				</div>
			</div>
		</c:if>

	</ycommerce:testId>
</div>